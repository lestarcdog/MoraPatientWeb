package hu.mora.migrate.dbload;

import net.iryndin.jdbf.core.DbfRecord;
import net.iryndin.jdbf.reader.DbfReader;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by cdog on 2016.12.10..
 */
public class JdbParserTest {

    @Test
    public void readStuff() throws Exception {
        InputStream stream = JdbParserTest.class.getResourceAsStream("/ELH_ENG.DBF");
        try (DbfReader reader = new DbfReader(stream)) {
            System.out.println(reader.getMetadata());
            reader.findFirstRecord();
            DbfRecord read = reader.read();
            System.out.println(read.toMap());
        }

    }
}
