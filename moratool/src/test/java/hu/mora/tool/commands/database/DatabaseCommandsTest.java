package hu.mora.tool.commands.database;

import hu.mora.tool.commands.SpringBase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseCommandsTest extends SpringBase {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Autowired
    DatabaseCommands underTest;

    @Test
    public void zipTest() throws Exception {
        underTest.saveDatabaseToPath("C:/temp");
    }
}