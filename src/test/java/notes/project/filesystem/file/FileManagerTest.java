package notes.project.filesystem.file;

import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.file.impl.FileManagerImpl;
import notes.project.filesystem.utils.PathHelper;
import notes.project.filesystem.utils.TestDataConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(pathHelper.createPathToCluster(any())).thenReturn(TestDataConstants.RESOLVED_PATH_FOR_CREATE_CLUSTER);
        fileManager.createCluster(TestDataConstants.CREATE_CLUSTER_TITLE);
        assertFileCreated(Path.of(TestDataConstants.RESOLVED_PATH_FOR_CREATE_CLUSTER));

        verify(pathHelper).createPathToCluster(TestDataConstants.CREATE_CLUSTER_TITLE);
    }

    private void assertFileCreated(Path path) throws IOException {
        assertTrue(Files.exists(path));
        Files.delete(path);
        Files.delete(Path.of(TestDataConstants.ROOT_DIRECTORY_PATH));
    }
}
