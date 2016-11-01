package hu.mora.dbmigrate;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import org.h2.Driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbPatientMigrate {

    private static final String CONNECTION_URL = "jdbc:h2:/C:/morapatient/db/morapatient";

    public static final LocalDate EPOCH_BIRTHDATE = LocalDate.of(1970, 1, 1);

    private Driver driver;
    private Connection connection;
    private PreparedStatement patientStmt;
    private PreparedStatement therapyStmt;

    public static void main(String[] args) throws IOException, SQLException {
        new DbPatientMigrate();


    }

    public DbPatientMigrate() throws SQLException {
        driver = new Driver();
        try {
            connectDb();
            readFile();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

    private void connectDb() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection(CONNECTION_URL, "sa", "sa");
        patientStmt = connection.prepareStatement("INSERT INTO PATIENTS(NAME,IS_MALE,BIRTH_DATE,PHONE,EMAIL,CITY,STREET) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        therapyStmt = connection.prepareStatement("INSERT INTO THERAPIES(THERAPY_DATE,THERAPY,TREATMENT,INTERVIEW,RESULT,PATIENT_ID,THERAPIST_ID) VALUES(?,?,?,?,?,?,1)");
    }

    private void readFile() throws IOException {
        //TODO to file reader
        Reader patientIn = new InputStreamReader(DbPatientMigrate.class.getResourceAsStream("/WPATIENT_IMO.csv"), Charsets.ISO_8859_1);
        Reader therapyIn = new InputStreamReader(DbPatientMigrate.class.getResourceAsStream("/THERAPY.csv"), Charsets.ISO_8859_1);
        //key is old id
        Map<Integer, MigratePatient> patients = new HashMap<>();

        //save patients
        try (BufferedReader reader = new BufferedReader(patientIn)) {
            String line;
            //skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                List<String> row = Splitter.on(";").splitToList(line);
                MigratePatient migratePatient = MigratePatient.createPatient(row);
                patients.put(migratePatient.getOldId(), migratePatient);
                try {
                    savePatient(migratePatient);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
        //save therapies
        int skipped = 0;
        try (BufferedReader reader = new BufferedReader(therapyIn)) {
            String line;
            //skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                List<String> row = Splitter.on(";").splitToList(line);
                MigrateTherapy therapy = MigrateTherapy.createTherapy(row);
                try {
                    //find patient by old id
                    MigratePatient patient = patients.get(therapy.getOldId());
                    if (patient == null) {
                        skipped++;
                        System.out.println("Not found :" + therapy.getOldId());
                    } else {
                        saveTherapy(therapy, patient.getNewId());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Totally skipped therapy: " + skipped);

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
