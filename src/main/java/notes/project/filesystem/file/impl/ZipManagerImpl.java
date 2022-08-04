package notes.project.filesystem.file.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
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
import notes.project.filesystem.utils.PathHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZipManagerImpl implements ZipManager {
    private final ApplicationProperties properties;
    private final FileManager fileManager;
    private final PathHelper pathHelper;

    @Override
    public void zipCreatedFile(CreatedFile createdFile) {
        String archivePath = createPathToArchive(createdFile.getExternalId());
        createZip(archivePath);
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(archivePath))) {
            createZipEntry(fileManager.readFile(createdFile), createdFile.getExternalId(), zipOutputStream);
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

    @Override
    public synchronized void zipFileForUpdate(CreatedFile createdFile, UUID versionFileExternalId) {
        String archivePath = createPathToArchive(versionFileExternalId);
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(archivePath))) {
            createZipEntry(fileManager.readFile(createdFile), versionFileExternalId, zipOutputStream);
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.DELETION_ERROR);
        }
    }

    @Override
    public synchronized void recreateFile(CreatedFile createdFile) {
        String path = createPathToArchive(createdFile.getExternalId());
        try {
            String content = readZipFile(path, createdFile.getExternalId().toString());
            fileManager.createFile(createdFile, content);
            FileUtils.delete(new File(path));
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.RECREATING_ERROR, exception.getMessage());
        }
    }

    @Override
    public void recreateFileWithPath(CreatedFile createdFile) {
        if(!Files.exists(pathHelper.createPathToCluster(createdFile.getDirectory().getCluster()))) {
            fileManager.createCluster(createdFile.getDirectory().getCluster());
        }
        fileManager.createDirectory(createdFile.getDirectory());
        recreateFile(createdFile);
    }

    @Override
    public void recreateDirectoryWithPath(Directory directory) {
        fileManager.createCluster(directory.getCluster());
        recreateDirectory(directory);
    }

    @Override
    public synchronized void recreateDirectory(Directory directory) {
        fileManager.createDirectory(directory);
        directory.getCreatedFiles().forEach(this::recreateFile);
    }

    @Override
    public synchronized void recreateCluster(Cluster cluster) {
        fileManager.createCluster(cluster);
        cluster.getDirectories().forEach(this::recreateDirectory);
    }

    @Override
    public synchronized void deleteZip(CreatedFile createdFile) {
        String pathToArchive = createPathToArchive(createdFile.getExternalId());
        try {
            Files.delete(Path.of(pathToArchive));
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.DELETION_ERROR, exception.getMessage());
        }
    }

    @Override
    public String readZipFile(UUID versionFileExternalId) {
        String pathToArchive = createPathToArchive(versionFileExternalId);
        try {
            return readZipFile(pathToArchive, versionFileExternalId.toString());
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.READING_ERROR, exception.getMessage());
        }
    }

    private String readZipFile(String archivePath, String zipEntryName) throws IOException {
        ZipFile zipFile = new ZipFile(archivePath);
        ZipEntry zipEntry = zipFile.getEntry(zipEntryName + FileResolution.TXT.getResolution());
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        inputStream.close();
        zipFile.close();
        return content;
    }

    private String createPathToArchive(UUID externalId) {
        return properties.getArchiveRoot() + "/" + externalId + FileResolution.ZIP.getResolution();
    }

    private void createZipEntry(String content, UUID fileExternalId, ZipOutputStream zipOutputStream) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileExternalId.toString() + FileResolution.TXT.getResolution());
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
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
