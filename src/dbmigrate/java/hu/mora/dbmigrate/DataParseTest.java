package hu.mora.dbmigrate;

import org.junit.Test;

import java.time.LocalDate;

import static hu.mora.dbmigrate.MigratePatient.PATIENT_CSV_DATE;

public class DataParseTest {

    @Test
    public void shouldPassMoraDateFormat() {
        String oldDate = "14-7-97";
        System.out.println(LocalDate.parse(oldDate, PATIENT_CSV_DATE).minusYears(100).toString());

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
