package hu.mora.tool.commands.database;


import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import hu.mora.tool.commands.AbstractCommands;
import hu.mora.tool.exception.MoraException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DatabaseCommands extends AbstractCommands {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseCommands.class);

    public void saveDatabaseToPath(String path) throws MoraException {
        File db = moraPaths.database.databasePath().toFile();
        if (!db.exists()) {
            throw new MoraException("Az adatbázis még nincs létrehozva");
        }

        ZipOutputStream zipOutput;
        FileInputStream dbStream = null;
        try {
            dbStream = new FileInputStream(db);

            //get last part of path as filename
            Path backupDbPath = moraPaths.database.createBackupDbPath(path);
            createDirectoryStructureForFile(backupDbPath);

            zipOutput = new ZipOutputStream(new FileOutputStream(backupDbPath + ".zip"));

            //add entry
            zipOutput.putNextEntry(new ZipEntry(backupDbPath.getFileName().toString()));
            ByteStreams.copy(dbStream, zipOutput);
            zipOutput.closeEntry();

            zipOutput.close();
        } catch (IOException e) {
            throw new MoraException("A " + path + " útvonalra nem menthető az adatbázis", e);
        } finally {
            Closeables.closeQuietly(dbStream);
        }


    }

}
