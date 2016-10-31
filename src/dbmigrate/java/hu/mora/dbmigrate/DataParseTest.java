package hu.mora.dbmigrate;

import org.junit.Test;

import java.time.LocalDate;

public class DataParseTest {

    @Test
    public void shouldPassMoraDateFormat() {
        String oldDate = "14-7-97";
        System.out.println(LocalDate.parse(oldDate, DbPatientMigrate.CSV_DATE).minusYears(100).toString());

    }
}
