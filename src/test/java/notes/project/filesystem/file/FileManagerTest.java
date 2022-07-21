package notes.project.filesystem.file;

import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.file.impl.FileManagerImpl;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.PathHelper;
import notes.project.filesystem.utils.TestDataConstants;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static notes.project.filesystem.utils.TestDataConstants.*;

@ExtendWith(MockitoExtension.class)
class FileManagerTest {
    @Mock
    private PathHelper pathHelper;

    private FileManager fileManager;

    @BeforeEach
    void init() {
        fileManager = new FileManagerImpl(pathHelper);
    }

    @Test
    void createClusterSuccess() throws IOException {
        when(pathHelper.createPathToCluster(any())).thenReturn(RESOLVED_PATH_FOR_CREATE_CLUSTER);
        fileManager.createCluster(DbUtils.cluster());
        assertFileCreated(Path.of(RESOLVED_PATH_FOR_CREATE_CLUSTER));

        verify(pathHelper).createPathToCluster(CREATED_CLUSTER_EXTERNAL_ID.toString());
    }

    @Test
    void createDirectorySuccess() throws IOException {
        when(pathHelper.createPathToDirectory(any(), any())).thenReturn(RESOLVED_PATH_FORE_CREATE_DIRECTORY);
        fileManager.createDirectory(DbUtils.directory());
        assertFileCreated(Path.of(RESOLVED_PATH_FORE_CREATE_DIRECTORY));

        verify(pathHelper).createPathToDirectory(
            CREATED_CLUSTER_EXTERNAL_ID.toString(),
            DIRECTORY_EXTERNAL_ID.toString()
        );
    }

    private void assertFileCreated(Path path) throws IOException {
        assertTrue(Files.exists(path));
        FileUtils.deleteDirectory(new File(ROOT_DIRECTORY_PATH));
    }
}
