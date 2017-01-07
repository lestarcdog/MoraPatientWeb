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


    private static final String MORA_PATETIENT_ROOT_DIR = "morapatient";
    private static final String NOVA_DB_ROOT_DIR = "novadb";
    private static final String WILDFLY_DIR = "wildfly";
    private static final String TOOL_DIR = "tool";
    private static final String DB_DIR = "db";
    private static final String NOVADB_EXE = "novaDB1.exe";

    public final WildflyPaths wildfly = new WildflyPaths();
    public final DatabasePaths database = new DatabasePaths();

    @Autowired
    ConfigurationRepository configurationRepository;

    /**
     * Application config
     */
    private Config config;

    @PostConstruct
    public void init() {
        config = configurationRepository.config();
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
    public boolean isMoraPatientHomeDir(String rootDirPath) {
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

    public boolean isNovaDbHomeDir(String rootDirPath) {
        File rootDir = new File(rootDirPath);
        if (rootDir != null & rootDir.isDirectory()) {
            Path rootPath = rootDir.toPath();
            return Files.exists(rootPath.resolve(NOVADB_EXE));
        } else {
            return false;
        }
    }

    public class WildflyPaths {

        private WildflyPaths() {
        }

        public Path jbossCliPath() {
            return config.getMoraPatientHomeDirPath().resolve(WILDFLY_DIR).resolve("bin").resolve("jboss-cli.bat");
        }

        public Path startBatPath() {
            return config.getMoraPatientHomeDirPath().resolve(WILDFLY_DIR).resolve("bin").resolve("standalone.bat");
        }
    }

    public class DatabasePaths {

        private final String DATABASE_NAME = "morapatient.h2.db";

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        private DatabasePaths() {
        }

        public Path moraPatientsDatabase() {
            return config.getMoraPatientHomeDirPath().resolve(DB_DIR).resolve(DATABASE_NAME);
        }

        public Path novaDbDatabase() {
            return config.getNovaDbHomeDirPath();
        }

        /**
         * Creates backup paths for MoraDatabase and NovaDb
         *
         * @param prefix path prefix (usually the external drive name e.g: E:/)
         * @return the backup database paths
         */
        public BackupDatabasePaths createDatabaseBackupPaths(String prefix) {
            String currentTime = LocalDateTime.now().format(formatter);
            // only the database needs to be saved
            String backup = "backup";
            Path moraPatientBackup = Paths.get(prefix).resolve(backup).resolve(MORA_PATETIENT_ROOT_DIR).resolve("db").resolve(DATABASE_NAME + "." + currentTime);
            // the whole directory needs to be saved
            Path novaDbBackup = Paths.get(prefix).resolve(backup).resolve(NOVA_DB_ROOT_DIR).resolve(NOVA_DB_ROOT_DIR + "." + currentTime);
            return new BackupDatabasePaths(moraPatientBackup, novaDbBackup);
        }

    }

    public static class BackupDatabasePaths {
        public final Path moraPatientBackupPath;
        public final Path novaDbBackupPath;

        public BackupDatabasePaths(Path moraPatientBackupPath, Path novaDbBackupPath) {
            this.moraPatientBackupPath = moraPatientBackupPath;
            this.novaDbBackupPath = novaDbBackupPath;
        }
    }
}
