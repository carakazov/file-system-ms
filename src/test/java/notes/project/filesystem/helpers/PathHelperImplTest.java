package notes.project.filesystem.helpers;

import java.nio.file.Path;

import notes.project.filesystem.utils.ApplicationPropertiesUtils;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.PathHelper;
import notes.project.filesystem.utils.impl.PathHelperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static notes.project.filesystem.utils.TestDataConstants.*;

class PathHelperImplTest {
    private PathHelper pathHelper;

    @BeforeEach
    void init() {
        pathHelper = new PathHelperImpl(ApplicationPropertiesUtils.applicationPropertiesForPathHelper());
    }

    @Test
    void createPathToClusterSuccess() {
        Path actual = pathHelper.createPathToCluster(DbUtils.cluster());
        assertEquals(RESOLVED_PATH_FOR_CREATE_CLUSTER, actual);
    }

    @Test
    void createPathToDirectorySuccess() {
        Path actual = pathHelper.createPathToDirectory(DbUtils.directory());
        assertEquals(RESOLVED_PATH_FOR_CREATE_DIRECTORY, actual);
    }

    @Test
    void createPathToFileSuccess() {
        Path actual = pathHelper.createPathToFile(DbUtils.createdFile());
        assertEquals(RESOLVED_PATH_FOR_CREATE_FILE, actual);
    }
}
