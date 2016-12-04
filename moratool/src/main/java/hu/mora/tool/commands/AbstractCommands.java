package hu.mora.tool.commands;

import hu.mora.tool.configuration.MoraPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public abstract class AbstractCommands {

    @Autowired
    protected MoraPaths moraPaths;

    protected void createDirectoryStructureForFile(Path filePath) throws IOException {
        Path path;
        if (filePath.getNameCount() > 1) {
            path = filePath.getRoot().resolve(filePath.subpath(0, filePath.getNameCount() - 1));
        } else {
            path = filePath;
        }

        if (!path.toFile().exists()) {
            Files.createDirectories(path);
        }

    }

}
