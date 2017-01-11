package hu.mora.web.model.novadb.beap;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PersonTest {

    @Test
    public void name() throws Exception {
        LocalDate date = LocalDate.parse("19.09.1999", DateTimeFormatter.ofPattern("d.M.yyyy"));

    }
}