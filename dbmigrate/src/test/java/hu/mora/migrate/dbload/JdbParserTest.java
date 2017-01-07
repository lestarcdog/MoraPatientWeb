package hu.mora.migrate.dbload;

import net.iryndin.jdbf.core.DbfRecord;
import net.iryndin.jdbf.reader.DbfReader;
import nl.knaw.dans.common.dbflib.Table;
import nl.knaw.dans.common.dbflib.Version;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;

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


    @Test
    public void dansDbf() throws Exception {
        File dbRoot = new File("G:\\agotakutya\\Medtronik\\Med-Tronik\\WPATIENT.DBF");
        Table table = new Table(dbRoot, Version.CLIPPER_5, Collections.emptyList());
        table.open();
        System.out.println(table.getRecordCount());

    }
}
