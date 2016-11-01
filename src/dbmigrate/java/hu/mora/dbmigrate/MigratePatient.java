package hu.mora.dbmigrate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static hu.mora.dbmigrate.DbPatientMigrate.EPOCH_BIRTHDATE;

public class MigratePatient {

    public static final DateTimeFormatter PATIENT_CSV_DATE = DateTimeFormatter.ofPattern("d-M-uu");

    private Integer newId;

    private Integer oldId;

    private String name;

    private Boolean isMale;

    private LocalDate birthDate;

    private String birthPlace;

    private String phone;

    private String email;

    private String city;

    private String street;

    public Integer getNewId() {
        return newId;
    }

    public void setNewId(Integer newId) {
        this.newId = newId;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getMale() {
        return isMale;
    }

    public void setMale(Boolean male) {
        isMale = male;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", isMale=" + isMale +
                ", birthDate=" + birthDate +
                ", birthPlace='" + birthPlace + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }


    public static MigratePatient createPatient(List<String> row) {
        MigratePatient p = new MigratePatient();
        p.setOldId(getOldId(row));
        p.setName(getName(row));
        p.setMale(getMale(row));
        p.setBirthDate(getBirthDate(row));
        p.setStreet(getStreet(row));
        p.setCity(getCity(row));
        p.setPhone(getPhone(row));
        p.setEmail(getEmail(row));

        return p;
    }

    private static Integer getOldId(List<String> row) {
        return Integer.valueOf(row.get(0));
    }

    private static String getName(List<String> row) {
        return row.get(2) + " " + row.get(3);
    }

    private static String getCity(List<String> row) {
        return row.get(7);
    }

    private static String getStreet(List<String> row) {
        return row.get(4);
    }

    private static String getPhone(List<String> row) {
        return row.get(8);
    }

    private static LocalDate getBirthDate(List<String> row) {
        String date = row.get(10);
        //dd-MM-yy
        if (date == null || date.isEmpty()) {
            return EPOCH_BIRTHDATE;
        } else {
            try {
                LocalDate twok = LocalDate.parse(date, PATIENT_CSV_DATE);
                return twok.minusYears(100);
            } catch (Exception e) {
                return EPOCH_BIRTHDATE;
            }
        }
    }

    private static Boolean getMale(List<String> row) {
        return row.get(11).equalsIgnoreCase("M");
    }

    private static String getEmail(List<String> row) {
        return row.get(28);
    }

    public static boolean validate(MigratePatient p) {
        return p.getName() != null && !p.getName().isEmpty();
    }
}
