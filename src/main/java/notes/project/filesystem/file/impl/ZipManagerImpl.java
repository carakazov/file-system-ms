package notes.project.filesystem.file.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.FileResolution;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZipManagerImpl implements ZipManager {
    private final ApplicationProperties properties;
    private final FileManager fileManager;

    @Override
    public void zipCreatedFile(CreatedFile createdFile) {
        String archivePath = properties.getArchiveRoot() + "/" + createdFile.getExternalId().toString() + FileResolution.ZIP.getResolution();
        createZip(archivePath);
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(archivePath))) {
            createZipEntry(createdFile, zipOutputStream);
            fileManager.deleteFile(createdFile);
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.DELETION_ERROR);
        }
    }

    @Override
    public void zipDirectory(Directory directory) {
        directory.getCreatedFiles().stream()
            .filter(item -> Boolean.FALSE.equals(item.getDeleted()))
            .forEach(this::zipCreatedFile);
        fileManager.deleteDirectory(directory);
    }

    @Override
    public void zipCluster(Cluster cluster) {
        cluster.getDirectories().stream()
            .filter(item -> Boolean.FALSE.equals(item.getDeleted()))
            .forEach(this::zipDirectory);
        fileManager.deleteCluster(cluster);
    }

    private void createZipEntry(CreatedFile createdFile, ZipOutputStream zipOutputStream) throws IOException {
        ZipEntry zipEntry = new ZipEntry(createdFile.getExternalId().toString() + FileResolution.TXT.getResolution());
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(fileManager.readFile(createdFile).getBytes(StandardCharsets.UTF_8));
        zipOutputStream.closeEntry();
    }

    private void createZip(String archivePath) {
        try {
            Files.createFile(Path.of(archivePath));
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.DELETION_ERROR);
        }
    }
}
