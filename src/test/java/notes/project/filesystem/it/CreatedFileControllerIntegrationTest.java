package notes.project.filesystem.it;

import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.filesystem.controller.CreatedFileController;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.TestUtils;
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
class CreatedFileControllerIntegrationTest extends AbstractIntegrationTest {
    private MockMvc mockMvc;

    @Inject
    private CreatedFileController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void createFileSuccess() throws Exception {
        testEntityManager.merge(DbUtils.cluster());

        Cluster cluster = testEntityManager.getEntityManager().createQuery(
            "select c from clusters c where c.title = :title",
            Cluster.class
        ).setParameter("title", DbUtils.cluster().getTitle()).getSingleResult();

        testEntityManager.merge(DbUtils.directory().setCluster(cluster));

        createDirectoryForFile();

        mockMvc.perform(MockMvcRequestBuilders.post("/file")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.getClasspathResource("api/AddFileRequestDto.json")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("my file test"));

        CreatedFile createdFile = testEntityManager.getEntityManager().createQuery(
            "select cf from created_files cf where cf.title = :title",
            CreatedFile.class
        ).setParameter("title", "my file test").getSingleResult();

        assertNotNull(createdFile);
    }
}
