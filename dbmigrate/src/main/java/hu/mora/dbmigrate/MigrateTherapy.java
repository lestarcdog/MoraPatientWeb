package hu.mora.dbmigrate;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

class MigrateTherapy {

    public static final DateTimeFormatter THERAPY_CSV_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");

    private Integer oldId;
    private String interview;
    private String result;
    private String treatment;
    private String therapy;
    private LocalDate therapyDate;


    public String getTherapy() {
        return therapy;
    }

    public void setTherapy(String therapy) {
        this.therapy = therapy;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public String getInterview() {
        return interview;
    }

    public void setInterview(String interview) {
        this.interview = interview;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public LocalDate getTherapyDate() {
        return therapyDate;
    }

    public void setTherapyDate(LocalDate therapyDate) {
        this.therapyDate = therapyDate;
    }

    public static MigrateTherapy createTherapy(List<String> row) {
        MigrateTherapy t = new MigrateTherapy();
        t.setOldId(getOldId(row));
        t.setInterview(getInterview(row));
        t.setTreatment(getTreatment(row));
        t.setTherapy(getTherapy(row));
        t.setResult(getResult(row));
        t.setTherapyDate(getDate(row));
        return t;
    }

    private static LocalDate getDate(List<String> row) {
        try {
            return LocalDate.parse(row.get(1), THERAPY_CSV_DATE);
        } catch (DateTimeParseException e) {
            return DbPatientMigrate.EPOCH_BIRTHDATE;
        }
    }

    private static String getResult(List<String> row) {
        return row.get(7);
    }

    private static String getTherapy(List<String> row) {
        return row.get(5);
    }

    private static String getTreatment(List<String> row) {
        return row.get(6);
    }

    private static String getInterview(List<String> row) {
        return row.get(4);
    }

    private static Integer getOldId(List<String> row) {
        return Double.valueOf(row.get(0)).intValue();
    }

    @Override
    public String toString() {
        return "MigrateTherapy{" +
                "oldId=" + oldId +
                ", interview='" + interview + '\'' +
                ", result='" + result + '\'' +
                ", treatment='" + treatment + '\'' +
                ", therapy='" + therapy + '\'' +
                ", therapyDate=" + therapyDate +
                '}';
    }
}