package hu.mora.tool.configuration;

import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class MoraPaths {

    private static final String WILDFLY_DIR = "wildfly";
    private static final String TOOL_DIR = "tool";
    private static final String DB_DIR = "db";

    private Path homeDir;

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

    public String getHomeDir() {
        return homeDir != null ? homeDir.toString() : null;
    }


    public String jbossCliPath() {
        return homeDir.resolve(WILDFLY_DIR).resolve("bin").resolve("jboss-cli.bat").toString();
    }
}
