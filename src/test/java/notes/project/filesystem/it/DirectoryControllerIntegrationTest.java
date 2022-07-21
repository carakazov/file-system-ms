package notes.project.filesystem.it;

import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.filesystem.controller.DirectoryController;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("it")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@Transactional
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
            .andExpect(jsonPath("$.clusterName").value("3edce674-f3cf-4650-ad89-1bdd44b3f26a"))
            .andExpect(jsonPath("$.directoryName").value("test directory"));

        Directory directory = testEntityManager.getEntityManager().createQuery(
            "select d from directories d where d.title = :title",
            Directory.class
        ).setParameter("title", "test directory").getSingleResult();

        assertNotNull(directory);
    }
}
