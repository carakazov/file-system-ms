package notes.project.filesystem.it;

import java.util.Collections;
import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.filesystem.controller.ClusterController;
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

import static notes.project.filesystem.utils.TestDataConstants.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("it")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ClusterControllerIntegrationTest extends AbstractIntegrationTest {
    private MockMvc mockMvc;

    @Inject
    private ClusterController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }


    @Test
    void createClusterSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cluster")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.getClasspathResource("api/CreateClusterRequest.json")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("new cluster"));

        Cluster cluster = findClusterByTitle("new cluster");

        assertNotNull(cluster);
    }

    @Test
    void deleteClusterSuccess() throws Exception {
        CreatedFile createdFile = DbUtils.createdFile();
        Directory directory = DbUtils.directory();
        Cluster cluster = DbUtils.cluster();


        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);
        testEntityManager.merge(createdFile);

        cluster.setDirectories(Collections.singletonList(directory));
        directory.setCreatedFiles(Collections.singletonList(createdFile));

        testEntityManager.merge(directory);
        testEntityManager.merge(cluster);

        createDirectoryWithFile();
        mockMvc.perform(MockMvcRequestBuilders.delete("/cluster/3edce674-f3cf-4650-ad89-1bdd44b3f26a"));


        assertFileCreatedThenDelete(TestDataConstants.ZIPPED_CREATED_FILE_PATH);
        assertFileDeleted(TestDataConstants.RESOLVED_PATH_FOR_CREATE_CLUSTER);
    }

    @Test
    void readCluster() throws Exception {
        Cluster cluster = DbUtils.cluster();
        Directory directory = DbUtils.directory();

        testEntityManager.merge(cluster);
        testEntityManager.merge(directory);

        cluster.setDirectories(Collections.singletonList(directory));

        testEntityManager.merge(cluster);

        mockMvc.perform(MockMvcRequestBuilders.get("/cluster/3edce674-f3cf-4650-ad89-1bdd44b3f26a"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(CLUSTER_TITLE))
            .andExpect(jsonPath("$.externalId").value(CLUSTER_EXTERNAL_ID_STRING))
            .andExpect(jsonPath("$.directories[0].title").value(DIRECTORY_TITLE))
            .andExpect(jsonPath("$.directories[0].externalId").value(DIRECTORY_EXTERNAL_ID_STRING));
    }

    @Test
    void getClusterDeleteHistoryWhenHistoryExistsSuccess() throws Exception {
        testEntityManager.merge(DbUtils.cluster());
        testEntityManager.merge(DbUtils.deleteClusterHistory());

        mockMvc.perform(MockMvcRequestBuilders.get("/cluster/3edce674-f3cf-4650-ad89-1bdd44b3f26a/deleteHistory"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.objectTitle").value(CLUSTER_TITLE))
            .andExpect(jsonPath("$.currentState").value("DELETED"))
            .andExpect(jsonPath("$.history[0].event").value("DELETED"));
    }

    @Test
    void getClustetDeleteHistoryWhenHistoryDoesNotExits() throws Exception {
        testEntityManager.merge(DbUtils.cluster());

        mockMvc.perform(MockMvcRequestBuilders.get("/cluster/3edce674-f3cf-4650-ad89-1bdd44b3f26a/deleteHistory"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.objectTitle").value(CLUSTER_TITLE))
            .andExpect(jsonPath("$.currentState").value("CREATED"));
    }
}
