package hu.mora.tool.path;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class MoraHomeVerifier {

    private static final String WILDFLY_DIR = "wildfly";
    private static final String TOOL_DIR = "tool";
    private static final String DB_DIR = "db";

    /**
     * Has
     * <ul>
     * <li>wildfly</li>
     * <li>tool</li>
     * <li>db</li>
     * </ul>
     * directories under the root dir.
     *
     * @param rootDir root dir
     */
    public static boolean isHomeDirectory(File rootDir) {
        if (rootDir != null && rootDir.isDirectory()) {
            Path rootPath = rootDir.toPath();
            boolean allExists = Files.exists(rootPath.resolve(WILDFLY_DIR));
            allExists &= Files.exists(rootPath.resolve(TOOL_DIR));
            return allExists & Files.exists(rootPath.resolve(DB_DIR));
        } else {
            return false;
        }
    }
}
