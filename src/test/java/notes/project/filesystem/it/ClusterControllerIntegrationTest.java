package notes.project.filesystem.it;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;
import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.filesystem.controller.ClusterController;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.utils.DbUtils;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("it")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@Transactional
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

        deleteCluster(findClusterByTitle("new cluster"));
    }

    private Cluster findClusterByTitle(String title) {
        return testEntityManager.getEntityManager().createQuery(
            "select c from clusters c where c.title = :title",
            Cluster.class
        ).setParameter("title", title).getSingleResult();
    }
}
