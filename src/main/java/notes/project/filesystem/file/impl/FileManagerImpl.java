package notes.project.filesystem.file.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.utils.PathHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileManagerImpl implements FileManager {
    private final PathHelper pathHelper;

    @Override
    public void createCluster(String title) {
        create(pathHelper.createPathToCluster(title));
    }

    @Override
    public void createDirectory(String clusterTitle, String directoryTitle) {
        create(pathHelper.createPathToDirectory(clusterTitle, directoryTitle));
    }

    private synchronized void create(String fullPath) {
        try {
            Files.createDirectories(Path.of(fullPath));
        } catch (IOException e) {
            throw new FileSystemException(ExceptionCode.CLUSTER_CREATION_ERROR, e.getMessage());
        }
    }
}
