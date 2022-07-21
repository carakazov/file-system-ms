package notes.project.filesystem.file.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.utils.PathHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
            if(!Files.exists(clusterPath)) {
                throw new FileSystemException(ExceptionCode.CLUSTER_DOES_NOT_EXISTS);
            }
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
            if(!Files.exists(directoryPath)) {
                throw new FileSystemException(ExceptionCode.DIRECTORY_DOES_NOT_EXISTS);
            }
            Path fullPath = pathHelper.createPathToFile(createdFile);
            Files.createFile(fullPath);
            Files.write(fullPath, Collections.singleton(content), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileSystemException(ExceptionCode.CREATION_ERROR, e.getMessage());
        }
    }
}
