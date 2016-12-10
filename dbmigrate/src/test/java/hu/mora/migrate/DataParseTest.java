package hu.mora.migrate;

import org.junit.Test;

import java.time.LocalDate;



public class DataParseTest {

    @Test
    public void shouldPassMoraDateFormat() {
        String oldDate = "14-7-97";
        System.out.println(LocalDate.parse(oldDate, MigratePatient.PATIENT_CSV_DATE).minusYears(100).toString());

    }

    @Test
    public void shouldPassTherapyDate() {
        String date = "20040927";
        System.out.println(LocalDate.parse(date, MigrateTherapy.THERAPY_CSV_DATE).toString());

    }

    @Test
    public void parse() {
        System.out.println(Double.valueOf("28.00000000").intValue());
    }

}
