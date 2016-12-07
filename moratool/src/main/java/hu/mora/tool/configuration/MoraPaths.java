package hu.mora.tool.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MoraPaths {

    private static final Logger LOG = LoggerFactory.getLogger(MoraPaths.class);


    private static final String ROOTDIR_NAME = "MoraPatient";
    private static final String WILDFLY_DIR = "wildfly";
    private static final String TOOL_DIR = "tool";
    private static final String DB_DIR = "db";

    public final WildflyPaths wildfly = new WildflyPaths();
    public final DatabasePaths database = new DatabasePaths();

    private Path homeDir;

    @Autowired
    ConfigurationRepository configurationRepository;

    @PostConstruct
    public void init() {
        String homeDirectory = configurationRepository.homeDirectory();
        LOG.info("Home dir is set to {}", homeDirectory);
        homeDir = Paths.get(homeDirectory);
    }

    /**
     * Has
     * <ul>
     * <li>wildfly</li>
     * <li>tool</li>
     * <li>db</li>
     * </ul>
     * directories under the root dir.
     *
     * @param rootDirPath root dir
     */
    public boolean isHomeDirectory(String rootDirPath) {
        File rootDir = new File(rootDirPath);
        if (rootDir != null && rootDir.isDirectory()) {
            Path rootPath = rootDir.toPath();
            boolean allExists = Files.exists(rootPath.resolve(WILDFLY_DIR));
            allExists &= Files.exists(rootPath.resolve(TOOL_DIR));
            return allExists & Files.exists(rootPath.resolve(DB_DIR));
        } else {
            return false;
        }
    }

    public void setHomeDir(File dir) {
        homeDir = dir.toPath();
    }

    public Path getHomeDir() {
        return homeDir != null ? homeDir : null;
    }


    public class WildflyPaths {

        private WildflyPaths() {
        }

        public Path jbossCliPath() {
            return homeDir.resolve(WILDFLY_DIR).resolve("bin").resolve("jboss-cli.bat");
        }

        public Path startBatPath() {
            return homeDir.resolve(WILDFLY_DIR).resolve("bin").resolve("standalone.bat");
        }
    }

    public class DatabasePaths {

        private final String DATABASE_NAME = "morapatient.h2.db";

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        private DatabasePaths() {
        }

        public Path databasePath() {
            return homeDir.resolve(DB_DIR).resolve(DATABASE_NAME);
        }

        public Path createBackupDbPath(String prefix) {
            String currentTime = LocalDateTime.now().format(formatter);
            return Paths.get(prefix).resolve(ROOTDIR_NAME).resolve("backup").resolve("db").resolve(DATABASE_NAME + "." + currentTime);
        }
    }
}
