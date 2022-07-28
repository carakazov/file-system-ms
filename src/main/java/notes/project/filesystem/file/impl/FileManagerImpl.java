package notes.project.filesystem.file.impl;

import liquibase.pro.packaged.P;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.FileResolution;
import notes.project.filesystem.utils.PathHelper;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileManagerImpl implements FileManager {
    private final PathHelper pathHelper;

    @Override
    public synchronized void createCluster(Cluster cluster) {
        try {
            Files.createDirectories(pathHelper.createPathToCluster(cluster));
        } catch (IOException e) {
            throw new FileSystemException(ExceptionCode.CREATION_ERROR, e.getMessage());
        }
    }

    @Override
    public synchronized void createDirectory(Directory directory) {
        try{
            Path clusterPath = pathHelper.createPathToCluster(directory.getCluster());
            checkFileExists(clusterPath, new FileSystemException(ExceptionCode.CLUSTER_DOES_NOT_EXIST));
            Path fullPath = pathHelper.createPathToDirectory(directory);
            Files.createDirectories((fullPath));
        }catch (IOException e) {
            throw new FileSystemException(ExceptionCode.CREATION_ERROR, e.getMessage());
        }
    }

    @Override
    public synchronized void createFile(CreatedFile createdFile, String content) {
        try {
            Path directoryPath = pathHelper.createPathToDirectory(createdFile.getDirectory());
            checkFileExists(directoryPath, new FileSystemException(ExceptionCode.DIRECTORY_DOES_NOT_EXIST));
            Path fullPath = pathHelper.createPathToFile(createdFile);
            Files.createFile(fullPath);
            Files.write(fullPath, Collections.singleton(content), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileSystemException(ExceptionCode.CREATION_ERROR, e.getMessage());
        }
    }

    @Override
    public synchronized String readFile(CreatedFile createdFile) {
        Path filePath = pathHelper.createPathToFile(createdFile);
        try(FileInputStream fileInputStream = new FileInputStream(filePath.toString())) {
            checkFileExists(filePath, new FileSystemException(ExceptionCode.FILE_DOES_NOT_EXISTS));
            return new String(fileInputStream.readAllBytes());
        } catch(IOException e) {
            throw new FileSystemException(ExceptionCode.READING_ERROR, e.getMessage());
        }
    }

    @Override
    public synchronized void deleteDirectory(Directory directory) {
        Path directoryPath = pathHelper.createPathToDirectory(directory);
        try {
            checkFileExists(directoryPath, new FileSystemException(ExceptionCode.DIRECTORY_DOES_NOT_EXIST));
            FileUtils.deleteDirectory(new File(directoryPath.toString()));
        } catch(IOException e) {
            throw new FileSystemException(ExceptionCode.DELETION_ERROR, e.getMessage());
        }
    }

    @Override
    public synchronized void deleteFile(CreatedFile createdFile) {
        Path filePath = pathHelper.createPathToFile(createdFile);
        try {
            checkFileExists(filePath, new FileSystemException(ExceptionCode.FILE_DOES_NOT_EXISTS));
            Files.delete(filePath);
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.DELETION_ERROR, exception.getMessage());
        }
    }

    @Override
    public synchronized void deleteCluster(Cluster cluster) {
        Path clusterPath = pathHelper.createPathToCluster(cluster);
        try {
            checkFileExists(clusterPath, new FileSystemException(ExceptionCode.CLUSTER_DOES_NOT_EXIST));
            FileUtils.deleteDirectory(new File(clusterPath.toString()));
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.DELETION_ERROR, exception.getMessage());
        }
    }

    @Override
    public synchronized void moveFile(CreatedFile file, Directory newDirectory) {
        Path oldPath = pathHelper.createPathToFile(file);
        Path directorPath = pathHelper.createPathToDirectory(newDirectory);
        Path newPath = Path.of(directorPath + "/" + file.getExternalId().toString() + FileResolution.TXT.getResolution());

        try {
            checkFileExists(oldPath, new FileSystemException(ExceptionCode.FILE_DOES_NOT_EXISTS));
            checkFileExists(directorPath, new FileSystemException(ExceptionCode.DIRECTORY_DOES_NOT_EXIST));
            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.REPLACING_ERROR);
        }
    }

    @Override
    public synchronized void updateFile(CreatedFile oldFile, String newContent) {
        Path path = pathHelper.createPathToFile(oldFile);
        try {
            Files.write(path, newContent.getBytes(StandardCharsets.UTF_8));
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.UPDATING_ERROR);
        }
    }

    private synchronized void checkFileExists(Path path, FileSystemException exception) {
        if(!Files.exists(path)) {
            throw exception;
        }
    }
}
