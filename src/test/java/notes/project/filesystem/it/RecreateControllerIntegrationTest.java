package notes.project.filesystem.it;

import java.util.Collections;
import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.filesystem.controller.RecreateController;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.TestDataConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("it")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecreateControllerIntegrationTest extends AbstractIntegrationTest {
    private MockMvc mockMvc;

    @Inject
    private RecreateController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void recreateFileWhenFullPathDeleted() throws Exception {
        Cluster cluster = DbUtils.cluster();
        Directory directory = DbUtils.directory();
        CreatedFile createdFile = DbUtils.createdFile();

        createZippedFile();

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        createdFile.setDeleted(Boolean.TRUE);
        directory.setCreatedFiles(Collections.singletonList(createdFile));
        directory.setDeleted(Boolean.TRUE);

        cluster.setDirectories(Collections.singletonList(directory));
        cluster.setDeleted(Boolean.TRUE);

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        performFileRecreate();
    }

    @Test
    void recreateFileWhenDirectoryDeleted() throws Exception {
        Cluster cluster = DbUtils.cluster();
        Directory directory = DbUtils.directory();
        CreatedFile createdFile = DbUtils.createdFile();

        createZippedFile();
        createCluster(cluster);

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        createdFile.setDeleted(Boolean.TRUE);
        directory.setCreatedFiles(Collections.singletonList(createdFile));
        directory.setDeleted(Boolean.TRUE);

        cluster.setDirectories(Collections.singletonList(directory));

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        performFileRecreate();
    }

    @Test
    void recreateFile() throws Exception {
        Cluster cluster = DbUtils.cluster();
        Directory directory = DbUtils.directory();
        CreatedFile createdFile = DbUtils.createdFile();

        createZippedFile();
        createDirectoryForFile();

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        createdFile.setDeleted(Boolean.TRUE);
        directory.setCreatedFiles(Collections.singletonList(createdFile));

        cluster.setDirectories(Collections.singletonList(directory));

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        performFileRecreate();
    }

    @Test
    void recreateDirectoryWhenClusterDeleted() throws Exception{
        Cluster cluster = DbUtils.cluster();
        Directory directory = DbUtils.directory();
        CreatedFile createdFile = DbUtils.createdFile();

        createZippedFile();
        createDirectoryForFile();

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        createdFile.setDeleted(Boolean.TRUE);
        directory.setCreatedFiles(Collections.singletonList(createdFile));
        directory.setDeleted(Boolean.TRUE);

        cluster.setDirectories(Collections.singletonList(directory));
        cluster.setDeleted(Boolean.TRUE);

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        performDirectoryRecreate();
    }

    @Test
    void recreateDirectory() throws Exception {
        Cluster cluster = DbUtils.cluster();
        Directory directory = DbUtils.directory();
        CreatedFile createdFile = DbUtils.createdFile();

        createZippedFile();
        createDirectoryForFile();

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        createdFile.setDeleted(Boolean.TRUE);
        directory.setCreatedFiles(Collections.singletonList(createdFile));
        directory.setDeleted(Boolean.TRUE);

        cluster.setDirectories(Collections.singletonList(directory));

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        performDirectoryRecreate();
    }

    @Test
    void recreateCluster() throws Exception {
        Cluster cluster = DbUtils.cluster();
        Directory directory = DbUtils.directory();
        CreatedFile createdFile = DbUtils.createdFile();

        createZippedFile();
        createDirectoryForFile();

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        createdFile.setDeleted(Boolean.TRUE);
        directory.setCreatedFiles(Collections.singletonList(createdFile));
        directory.setDeleted(Boolean.TRUE);

        cluster.setDirectories(Collections.singletonList(directory));
        cluster.setDeleted(Boolean.TRUE);

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        performClusterRecreate();
    }

    private void performClusterRecreate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/recreate/cluster/3edce674-f3cf-4650-ad89-1bdd44b3f26a"))
            .andExpect(status().isOk());
        assertFiles();
    }

    private void performDirectoryRecreate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/recreate/directory/c139de85-4d96-4f27-8648-8cc86c1286be"))
            .andExpect(status().isOk());
        assertFiles();
    }

    private void performFileRecreate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/recreate/file/2a99b6fe-44f2-4837-bbee-80fbe43f3076"))
            .andExpect(status().isOk());
        assertFiles();
    }

    private void assertFiles() throws Exception {
        assertFileDeleted(TestDataConstants.ZIPPED_CREATED_FILE_PATH);
        assertFileCreatedThenDelete(TestDataConstants.RESOLVED_PATH_FOR_CREATE_FILE);
    }
}
