package notes.project.filesystem.it;

import java.util.Collections;
import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.filesystem.controller.DirectoryController;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.TestDataConstants;
import notes.project.filesystem.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static notes.project.filesystem.utils.TestDataConstants.*;

@Tag("it")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DirectoryControllerIntegrationTest extends AbstractIntegrationTest {
    private MockMvc mockMvc;

    @Inject
    private DirectoryController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void createDirectorySuccess() throws Exception {
        Cluster cluster = DbUtils.cluster();
        testEntityManager.merge(cluster);

        createCluster(cluster);

        mockMvc.perform(MockMvcRequestBuilders.post("/directory")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.getClasspathResource("api/DirectoryCreationRequest.json")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.clusterName").value("cluster-title"))
            .andExpect(jsonPath("$.directoryName").value("test directory"));

        Directory directory = testEntityManager.getEntityManager().createQuery(
            "select d from directories d where d.title = :title",
            Directory.class
        ).setParameter("title", "test directory").getSingleResult();

        assertNotNull(directory);
    }

    @Test
    void deleteDirectorySuccess() throws Exception {
        createDirectoryWithFile();

        Directory directory = DbUtils.directory();
        CreatedFile createdFile = DbUtils.createdFile();

        testEntityManager.merge(DbUtils.cluster());
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        directory.setCreatedFiles(Collections.singletonList(createdFile));

        testEntityManager.merge(directory);

        mockMvc.perform(MockMvcRequestBuilders.delete("/directory/c139de85-4d96-4f27-8648-8cc86c1286be"));

        assertFileCreatedThenDelete(TestDataConstants.ZIPPED_CREATED_FILE_PATH);
        assertFileDeleted(TestDataConstants.RESOLVED_PATH_FOR_CREATE_DIRECTORY);
    }

    @Test
    void readDirectorySuccess() throws Exception {
        Directory directory = DbUtils.directory();
        CreatedFile createdFile = DbUtils.createdFile();

        testEntityManager.merge(DbUtils.cluster());
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        directory.setCreatedFiles(Collections.singletonList(createdFile));

        testEntityManager.merge(directory);

        mockMvc.perform(MockMvcRequestBuilders.get("/directory/c139de85-4d96-4f27-8648-8cc86c1286be"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(DIRECTORY_TITLE))
            .andExpect(jsonPath("$.externalId").value(DIRECTORY_EXTERNAL_ID_STRING))
            .andExpect(jsonPath("$.files[0].title").value(FILE_TITLE))
            .andExpect(jsonPath("$.files[0].externalId").value(FILE_EXTERNAL_ID_STRING));
    }
}
