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
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DbPatientMigrate {

    private static final String CONNECTION_URL = "jdbc:h2:/C:/morapatient/db/morapatient";

    public static final DateTimeFormatter CSV_DATE = DateTimeFormatter.ofPattern("d-M-uu");
    public static final LocalDate EPOCH_BIRTHDATE = LocalDate.of(1970, 1, 1);
    private Driver driver;
    private Connection connection;
    private PreparedStatement stmt;

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
        stmt = connection.prepareStatement("INSERT INTO PATIENTS(NAME,IS_MALE,BIRTH_DATE,PHONE,EMAIL,CITY,STREET) VALUES(?,?,?,?,?,?,?)");
    }

    private void readFile() throws IOException {
        //TODO to file reader
        Reader in = new InputStreamReader(DbPatientMigrate.class.getResourceAsStream("/WPATIENT_IMO.csv"), Charsets.ISO_8859_1);
        try (BufferedReader reader = new BufferedReader(in)) {
            String line;
            //skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                List<String> row = Splitter.on(";").splitToList(line);
                Patient patient = createPatient(row);
                try {
                    savePatient(patient);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void savePatient(Patient p) throws SQLException {
        if (validate(p)) {
            System.out.println("Saving patient: " + p.toString());
            stmt.setString(1, p.getName());
            stmt.setBoolean(2, p.getMale());
            stmt.setDate(3, Date.valueOf(p.getBirthDate()));
            stmt.setString(4, p.getPhone());
            stmt.setString(5, p.getEmail());
            stmt.setString(6, p.getCity());
            stmt.setString(7, p.getStreet());

            stmt.executeUpdate();
        }
    }

    private boolean validate(Patient p) {
        return p.getName() != null && !p.getName().isEmpty();
    }

    private Patient createPatient(List<String> row) {
        Patient p = new Patient();
        p.setName(getName(row));
        p.setMale(getMale(row));
        p.setBirthDate(getBirthDate(row));
        p.setStreet(getStreet(row));
        p.setCity(getCity(row));
        p.setPhone(getPhone(row));
        p.setEmail(getEmail(row));

        return p;
    }

    private String getName(List<String> row) {
        return row.get(2) + " " + row.get(3);
    }

    private String getCity(List<String> row) {
        return row.get(7);
    }

    private String getStreet(List<String> row) {
        return row.get(4);
    }

    private String getPhone(List<String> row) {
        return row.get(8);
    }

    private LocalDate getBirthDate(List<String> row) {
        String date = row.get(10);
        //dd-MM-yy
        if (date == null || date.isEmpty()) {
            return EPOCH_BIRTHDATE;
        } else {
            try {
                LocalDate twok = LocalDate.parse(date, CSV_DATE);
                return twok.minusYears(100);
            } catch (Exception e) {
                return EPOCH_BIRTHDATE;
            }
        }
    }

    private Boolean getMale(List<String> row) {
        return row.get(11).equalsIgnoreCase("M");
    }

    private String getEmail(List<String> row) {
        return row.get(28);
    }


}
