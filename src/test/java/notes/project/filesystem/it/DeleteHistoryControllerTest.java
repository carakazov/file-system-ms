package notes.project.filesystem.it;

import javax.inject.Inject;
import javax.transaction.Transactional;

import notes.project.filesystem.controller.DeleteHistoryController;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.DeleteHistory;
import notes.project.filesystem.utils.DbUtils;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("it")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DeleteHistoryControllerTest extends AbstractIntegrationTest {
    private MockMvc mockMvc;

    @Inject
    private DeleteHistoryController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void getDeleteHistoryWhenHistoryExists() throws Exception {
        CreatedFile createdFile = DbUtils.createdFile();
        DeleteHistory deleteHistory = DbUtils.deleteCreatedFileHistory();

        testEntityManager.merge(DbUtils.cluster());
        testEntityManager.merge(DbUtils.directory());
        testEntityManager.merge(createdFile);
        testEntityManager.merge(deleteHistory);

        mockMvc.perform(MockMvcRequestBuilders.get("/deleteHistory/file/2a99b6fe-44f2-4837-bbee-80fbe43f3076"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.objectTitle").value("file-title"))
            .andExpect(jsonPath("$.currentState").value("DELETED"))
            .andExpect(jsonPath("$.history[0].event").value("DELETED"));
    }


    @Test
    void getDeleteHistoryWhenHistoryDoesNotExist() throws Exception {
        CreatedFile createdFile = DbUtils.createdFile();

        testEntityManager.merge(DbUtils.cluster());
        testEntityManager.merge(DbUtils.directory());
        testEntityManager.merge(createdFile);

        mockMvc.perform(MockMvcRequestBuilders.get("/deleteHistory/file/2a99b6fe-44f2-4837-bbee-80fbe43f3076"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.objectTitle").value("file-title"))
            .andExpect(jsonPath("$.currentState").value("CREATED"));
    }
}
