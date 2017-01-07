package hu.mora.tool.commands.database;

import hu.mora.tool.commands.SpringBase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DatabaseCommandsTest extends SpringBase {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Autowired
    DatabaseCommands underTest;

    @Test
    public void zipTest() throws Exception {
        underTest.saveDatabaseToPath("C:/temp");
    }

    @Test
    public void walk() throws IOException {
        Path root = Paths.get("C:/NovaDb");
        Stream<Path> walk = Files.walk(root);
        walk.forEach(p -> System.out.println(p.toString().replace(root.toString() + File.separator, "")));
    }
}