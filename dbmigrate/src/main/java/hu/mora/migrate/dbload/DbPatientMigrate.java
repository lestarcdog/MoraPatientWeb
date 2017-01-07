package hu.mora.migrate.dbload;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class DbPatientMigrate {

    private final static String CONNECTION_URL = "jdbc:h2:/C:/MoraPatient/db/morapatient";

    static final LocalDate EPOCH_BIRTHDATE = LocalDate.of(1970, 1, 1);

    /**
     * Contains the patient data PK is the CODE_NR
     */
    private static final Reader PATIENT_CSV = new InputStreamReader(DbPatientMigrate.class.getResourceAsStream("/WPATIENT.csv"), Charsets.ISO_8859_1);

    /**
     * Contains the therapists note about the patients with various fields
     */
    private static final Reader THERAPIES_CSV = new InputStreamReader(DbPatientMigrate.class.getResourceAsStream("/THERAPY.csv"), Charsets.ISO_8859_1);

    /**
     * Contains the patients allergies, responsiveness to different materials
     */
    private static final Reader TESTS_CSV = new InputStreamReader(DbPatientMigrate.class.getResourceAsStream("/MELHDATA.csv"), Charsets.ISO_8859_1);

    private final boolean appendMode;
    private final String connectionUrl;
    private static final Splitter SPLITTER = Splitter.on(";");

    private Connection connection;
    private PreparedStatement patientStmt;
    private PreparedStatement therapyStmt;
    private PreparedStatement testsStmt;
    private PreparedStatement findElhTestStmt;
    private PreparedStatement findPatientByNameAndBdayStmt;
    private Map<Integer, String> elhEng = new HashMap<>();

    public static void main(String[] args) throws IOException, SQLException {
        boolean appendMode = true;
        String connectionUrl = CONNECTION_URL;
        if (args.length == 2) {
            connectionUrl = args[0];
            appendMode = Boolean.parseBoolean(args[1]);
        }

        new DbPatientMigrate(connectionUrl, appendMode);


    }

    public DbPatientMigrate(String connectionUrl, boolean appendMode) throws SQLException {
        this.appendMode = appendMode;
        this.connectionUrl = connectionUrl;
        System.out.println("Connection url is " + connectionUrl);
        System.out.println("Append mode is " + appendMode);
        try {
            connectDb();
            cacheElhEng();
            readFiles();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

    private void cacheElhEng() throws IOException {
        Reader ELHENG_CSV = new InputStreamReader(DbPatientMigrate.class.getResourceAsStream("/ELG_ENG.csv"), "Cp852");
        try (BufferedReader reader = new BufferedReader(ELHENG_CSV)) {
            reader.lines()
                    .map(SPLITTER::splitToList)
                    .forEach(row -> elhEng.put(Integer.valueOf(row.get(1)), row.get(5)));
        }
    }

    private void connectDb() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection(connectionUrl, "sa", "sa");
        patientStmt = connection.prepareStatement("INSERT INTO PATIENTS (NAME,IS_MALE,BIRTH_DATE,PHONE,EMAIL,CITY,STREET) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        therapyStmt = connection.prepareStatement("INSERT INTO THERAPIES (THERAPY_DATE,THERAPY,TREATMENT,INTERVIEW,RESULT,PATIENT_ID,THERAPIST_ID) VALUES (?,?,?,?,?,?,1)");
        testsStmt = connection.prepareStatement("INSERT INTO ELHELEMENTS_PATIENTS (PATIENT_ID,ELHELEMENTS_ID) VALUES (?,?)");
        findElhTestStmt = connection.prepareStatement("SELECT ID FROM ELHELEMENTS WHERE NAME_EN=?");
        findPatientByNameAndBdayStmt = connection.prepareStatement("SELECT ID FROM PATIENTS WHERE NAME = ? AND BIRTH_DATE = ?");
    }

    private void readFiles() throws IOException {
        //TODO to file reader
        //key is old id CODE_NR
        Map<Integer, MigratePatient> patients = new HashMap<>();

        //save patients
        savePatientsToDatabase(PATIENT_CSV, patients);

        //save therapies
        saveTherapiesToDatabase(THERAPIES_CSV, patients);

        //save test data
        saveElhTestsToDatabase(TESTS_CSV, patients);

    }

    private void savePatientsToDatabase(Reader patientIn, Map<Integer, MigratePatient> patients) throws IOException {
        try (BufferedReader reader = new BufferedReader(patientIn)) {
            String line;
            //skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                List<String> row = SPLITTER.splitToList(line);
                MigratePatient migratePatient = MigratePatient.createPatient(row);
                patients.put(migratePatient.getOldId(), migratePatient);
                try {
                    // our preconception is that the patient all fully loaded
                    // so no append is needed
                    if (!appendMode) {
                        //create newPatientId
                        savePatient(migratePatient);
                    } else {
                        //no newPatientId
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void saveTherapiesToDatabase(Reader therapyIn, Map<Integer, MigratePatient> patients) throws IOException {
        int skipped = 0;
        try (BufferedReader reader = new BufferedReader(therapyIn)) {
            String line;
            //skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                List<String> row = SPLITTER.splitToList(line);
                MigrateTherapy therapy = MigrateTherapy.createTherapy(row);
                try {
                    //find patient by old id
                    MigratePatient patient = patients.get(therapy.getOldId());
                    if (patient == null) {
                        skipped++;
                        System.out.println("Not found :" + therapy.getOldId());
                    } else {
                        if (appendMode) {
                            //no append to patient
                        } else {
                            saveTherapy(therapy, patient.getNewId());
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Totally skipped therapy: " + skipped);
    }

    private void saveElhTestsToDatabase(Reader testsIn, Map<Integer, MigratePatient> patients) throws IOException {
        try (BufferedReader reader = new BufferedReader(testsIn)) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> row = SPLITTER.splitToList(line);
                MigrateElhElementsMapping elh = MigrateElhElementsMapping.createFromRow(row);
                try {
                    Integer newElhTestId = findElhNewId(elh.getOldTestId());
                    if (appendMode) {
                        MigratePatient patient = patients.get(elh.getOldPatientId());
                        if (patient != null) {
                            Optional<Integer> newPatientId = findPatientByNameAndBday(patient);
                            if (newPatientId.isPresent()) {
                                saveTest(newPatientId.get(), newElhTestId);
                            }
                        } else {
                            System.out.println("No patient found for test " + elh.getOldPatientId());
                        }

                    } else {
                        Integer newPatientId = patients.get(elh.getOldPatientId()).getNewId();
                        saveTest(newPatientId, newElhTestId);
                    }
                } catch (SQLException e) {
                    if (e.getErrorCode() != 23505) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Optional<Integer> findPatientByNameAndBday(MigratePatient patient) throws SQLException {
        findPatientByNameAndBdayStmt.setString(1, patient.getName());
        findPatientByNameAndBdayStmt.setDate(2, Date.valueOf(patient.getBirthDate()));
        ResultSet rs = findPatientByNameAndBdayStmt.executeQuery();
        if (rs.next()) {
            return Optional.of(rs.getInt(1));
        } else {
            System.out.println("Patient not found " + patient.getName() + " " + patient.getBirthDate());
            return Optional.empty();
        }
    }

    private Integer findElhNewId(Integer oldElhTestId) throws SQLException {
        String engName = elhEng.get(Objects.requireNonNull(oldElhTestId));
        findElhTestStmt.setString(1, engName);
        ResultSet resultSet = findElhTestStmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            throw new IllegalArgumentException("Not valid old elh test id " + oldElhTestId + " - " + engName);
        }
    }

    private void saveTherapy(MigrateTherapy therapy, Integer newPatientId) throws SQLException {
        System.out.println("Saving therapy: " + therapy.toString());

        therapyStmt.setDate(1, Date.valueOf(therapy.getTherapyDate()));
        therapyStmt.setString(2, therapy.getTherapy());
        therapyStmt.setString(3, therapy.getTreatment());
        therapyStmt.setString(4, therapy.getInterview());
        therapyStmt.setString(5, therapy.getResult());
        therapyStmt.setInt(6, newPatientId);

        therapyStmt.executeUpdate();
    }

    private void saveTest(Integer newPatientId, Integer newElTestId) throws SQLException {
        testsStmt.setInt(1, newPatientId);
        testsStmt.setInt(2, newElTestId);

        testsStmt.executeUpdate();
    }

    private void savePatient(MigratePatient p) throws SQLException {
        if (MigratePatient.validate(p)) {
            System.out.println("Saving patient: " + p.toString());
            patientStmt.setString(1, p.getName());
            patientStmt.setBoolean(2, p.getMale());
            patientStmt.setDate(3, Date.valueOf(p.getBirthDate()));
            patientStmt.setString(4, p.getPhone());
            patientStmt.setString(5, p.getEmail());
            patientStmt.setString(6, p.getCity());
            patientStmt.setString(7, p.getStreet());

            patientStmt.executeUpdate();
            ResultSet rs = patientStmt.getGeneratedKeys();
            rs.next();
            p.setNewId(rs.getInt(1));
        }
    }


}
