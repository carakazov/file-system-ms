package notes.project.filesystem.it;

import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import notes.project.filesystem.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
@ActiveProfiles("it")
@DirtiesContext
public abstract class AbstractIntegrationTest {
    @Inject
    protected ObjectMapper objectMapper;

   //@Inject
    // protected TestEntityManager testEntityManager;
}
