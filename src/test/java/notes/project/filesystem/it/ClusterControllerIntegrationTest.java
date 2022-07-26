package notes.project.filesystem.it;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.UUID;
import javax.inject.Inject;
import javax.transaction.Transactional;

import liquibase.pro.packaged.E;
import notes.project.filesystem.controller.ClusterController;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.TestDataConstants;
import notes.project.filesystem.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
