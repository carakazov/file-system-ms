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
    public void createCluster(Cluster cluster) {
        create(pathHelper.createPathToCluster(cluster.getExternalId().toString()));
    }

    @Override
    public void createDirectory(Directory directory) {
        create(pathHelper.createPathToDirectory(
            directory.getCluster().getExternalId().toString(),
            directory.getExternalId().toString()
        ));
    }

    @Override
    public void createFile(CreatedFile createdFile, String content) {
        create(pathHelper.createPathToFile(
            createdFile.getDirectory().getCluster().getExternalId().toString(),
            createdFile.getDirectory().getExternalId().toString(),
            createdFile.getExternalId().toString()
        ), content);
    }

    private synchronized void create(String fullPath, String content) {
        try {
            Path path = Path.of(fullPath);
            if(Files.exists(path)) {
                throw new FileSystemException(ExceptionCode.OBJECT_ALREADY_EXISTS);
            }
            Files.createFile(path);
            Files.write(path, Collections.singleton(content), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileSystemException(ExceptionCode.CREATION_ERROR, e.getMessage());
        }
    }

    private synchronized void create(String fullPath) {
        try {
            if(Files.exists(Path.of(fullPath))) {
                throw new FileSystemException(ExceptionCode.OBJECT_ALREADY_EXISTS);
            }
            Files.createDirectories(Path.of(fullPath));
        } catch (IOException e) {
            throw new FileSystemException(ExceptionCode.CREATION_ERROR, e.getMessage());
        }
    }
}
