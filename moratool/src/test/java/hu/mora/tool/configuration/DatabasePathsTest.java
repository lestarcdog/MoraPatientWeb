package hu.mora.tool.configuration;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by cdog on 2017.01.09..
 */
public class DatabasePathsTest {

    @Test
    public void localDatetimeParsing() throws Exception {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        System.out.println(LocalDateTime.now().format(format));

        System.out.println(LocalDateTime.parse(LocalDateTime.now().format(format), format));

    }
}