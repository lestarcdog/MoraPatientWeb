package hu.mora.migrate.dbload;

import net.iryndin.jdbf.core.DbfRecord;
import net.iryndin.jdbf.reader.DbfReader;
import org.junit.Test;

import java.io.InputStream;

public class JdbParserTest {

    @Test
    public void jdbf() throws Exception {
        InputStream stream = JdbParserTest.class.getResourceAsStream("/THERAPIE.DBF");
        InputStream memo = JdbParserTest.class.getResourceAsStream("/THERAPIE.dbt");
        try (DbfReader reader = new DbfReader(stream, memo)) {
            //System.out.println(reader.getMetadata());
            reader.findFirstRecord();
            DbfRecord read = reader.read();
            System.out.println(read.toMap());
        }

    }
}
