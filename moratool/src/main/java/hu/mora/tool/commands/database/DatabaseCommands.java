package hu.mora.tool.commands.database;


import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;
import hu.mora.tool.commands.AbstractCommands;
import hu.mora.tool.configuration.MoraPaths;
import hu.mora.tool.exception.MoraException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class DatabaseCommands extends AbstractCommands {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseCommands.class);

    private static final String ZIP_EXT = ".zip";

    public void saveDatabaseToPath(String path) throws MoraException {
        File moraDb = moraPaths.database.moraPatientsDbPath().toFile();
        if (!moraDb.exists()) {
            throw new MoraException("Az MoraPatient elérési útvonal helytelen vagy az adatbázis még nincs létrehozva");
        }
        File novaDb = moraPaths.database.novaDbPath().toFile();
        if (!novaDb.exists()) {
            throw new MoraException("A NovaDb elérési útvonal helytelen vagy nem létezik");
        }

        MoraPaths.BackupDatabasePaths backupPaths = moraPaths.database.createDatabaseBackupPaths(path);

        //create directory structure for backup morapatient and novadb if needed
        try {
            createDirectoryStructureForFile(backupPaths.moraPatientBackupPath);
            createDirectoryStructureForFile(backupPaths.novaDbBackupPath);
        } catch (IOException e) {
            throw new MoraException("A biztonsági mentés nem hozható létre a megadott lemezre", e);
        }

        String moraDbZipPath = backupPaths.moraPatientBackupPath.toString() + ZIP_EXT;
        String novaDbZipPath = backupPaths.novaDbBackupPath.toString() + ZIP_EXT;
        try (
                // input file and output file
                FileInputStream moraDbFileStream = new FileInputStream(moraDb);
                ZipOutputStream moraZipOut = new ZipOutputStream(new FileOutputStream(moraDbZipPath));
                // multiple input files and one output file
                ZipOutputStream novaDbZipOut = new ZipOutputStream(new FileOutputStream(novaDbZipPath))
        ) {
            packMoraDb(moraDb, moraDbZipPath, moraDbFileStream, moraZipOut);
            packNovaDb(novaDb, novaDbZipPath, novaDbZipOut);


        } catch (IOException e) {
            throw new MoraException("A " + path + " útvonalra nem menthető az adatbázis", e);
        }


    }

    private void packNovaDb(File novaDb, String novaDbZipPath, ZipOutputStream novaDbZipOut) throws IOException {
        LOG.info("Backing up novaDb {} to {}", novaDb.getAbsolutePath(), novaDbZipPath);
        Path root = novaDb.toPath();
        String rootAsString = root.toString() + File.separator;
        Stream<Path> novaDbPaths = Files.walk(root);
        novaDbPaths.forEach(nova -> {
            //remove prefix from the full path. only the relative stays
            String relativePath = nova.toString().replace(rootAsString, "");
            LOG.debug("Packing {} as {}", nova.toString(), relativePath);
            //open source file to read if not directory
            if (!nova.toFile().isDirectory()) {
                try (FileInputStream noveDbFile = new FileInputStream(nova.toString())) {
                    novaDbZipOut.putNextEntry(new ZipEntry(relativePath));
                    ByteStreams.copy(noveDbFile, novaDbZipOut);
                    novaDbZipOut.closeEntry();
                } catch (IOException e) {
                    Throwables.propagate(e);
                }
            }
        });
    }

    private void packMoraDb(File moraDb, String moraDbZipPath, FileInputStream moraDbFileStream, ZipOutputStream moraZipOut) throws IOException {
        LOG.info("Backing up moraDb {} to {}", moraDb.getAbsolutePath(), moraDbZipPath);
        moraZipOut.putNextEntry(new ZipEntry(moraDb.getName()));
        ByteStreams.copy(moraDbFileStream, moraZipOut);
        moraZipOut.closeEntry();
    }

    private void unpackZipFile(ZipInputStream zipInput, Path dstPath) throws IOException {
        ZipEntry entry;
        while ((entry = zipInput.getNextEntry()) != null) {
            Path dstFilePath = dstPath.resolve(entry.getName());
            LOG.info("Unpacking {} to path {}", entry.getName(), dstFilePath.toString());

            try {
                Files.createDirectories(dstFilePath.getParent());
            } catch (FileAlreadyExistsException e) {
                // swallow
            }
            FileOutputStream fileOut = new FileOutputStream(dstFilePath.toFile());
            ByteStreams.copy(zipInput, fileOut);

            fileOut.close();
            zipInput.closeEntry();
        }

        zipInput.close();
    }

    public void restoreDatabase(Path inputZipFile, Path dstPath) throws IOException {
        if (!Files.exists(inputZipFile)) {
            throw new IOException("A file nem létezik: " + inputZipFile.toString());
        }

        ZipInputStream zipInput = new ZipInputStream(new FileInputStream(inputZipFile.toFile()));
        deleteDir(dstPath);

        unpackZipFile(zipInput, dstPath);
    }

    private void deleteDir(Path dir) throws IOException {
        if (Files.exists(dir)) {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }


                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }

            });
        }
    }

}
