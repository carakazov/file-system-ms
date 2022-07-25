package notes.project.filesystem.file.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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
import notes.project.filesystem.service.CreatedFileService;
import notes.project.filesystem.utils.PathHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZipManagerImpl implements ZipManager {
    private final ApplicationProperties applicationProperties;
    private final FileManager fileManager;

    @Override
    public synchronized void zipDirectory(Directory directory) {
        String pathToZip = createZipFile(directory.getExternalId());
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(pathToZip))) {
            for(CreatedFile file : directory.getCreatedFiles()) {
                readFileContentToZipEntry(file, zipOutputStream);
            }
            fileManager.deleteDirectory(directory);
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.CREATION_ERROR);
        }
    }

    @Override
    public synchronized void zipCreatedFile(CreatedFile createdFile) {
        String pathToZip = createZipFile(createdFile.getExternalId());
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(pathToZip))) {
            readFileContentToZipEntry(createdFile, zipOutputStream);
            fileManager.deleteFile(createdFile);
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.CREATION_ERROR);
        }
    }

    @Override
    public synchronized void zipCluster(Cluster cluster) {
        String pathToZip = createZipFile(cluster.getExternalId());
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(pathToZip))) {
            for(Directory directory : cluster.getDirectories()) {
                for(CreatedFile createdFile : directory.getCreatedFiles()) {
                    readFileContentToZipEntry(createdFile, zipOutputStream, directory.getExternalId().toString());
                }
            }
            fileManager.deleteCluster(cluster);
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.CREATION_ERROR);
        }
    }

    private synchronized void readFileContentToZipEntry(CreatedFile createdFile, ZipOutputStream zipOutputStream) throws IOException {
        readFileContentToZipEntry(createdFile, zipOutputStream, null);
    }

    private synchronized void readFileContentToZipEntry(CreatedFile createdFile, ZipOutputStream zipOutputStream, String additionalPath) throws IOException {
        StringBuilder builder = new StringBuilder();
        if(Objects.nonNull(additionalPath)) {
            builder.append(additionalPath).append("/");
        }
        builder.append(createdFile.getExternalId().toString()).append(FileResolution.TXT.getResolution());

        ZipEntry zipEntry = new ZipEntry(builder.toString());
        zipOutputStream.putNextEntry(zipEntry);
        byte[] content = fileManager.readFile(createdFile).getBytes(StandardCharsets.UTF_8);
        zipOutputStream.write(content);
    }

    private synchronized String createZipFile(UUID objectExternalId)  {
        try {
            Path zipPath = Path.of(applicationProperties.getArchiveRoot() + "/" + objectExternalId + FileResolution.ZIP.getResolution());
            Files.createFile(zipPath);
            return zipPath.toString();
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.CREATION_ERROR, exception.getMessage());
        }
    }
}
