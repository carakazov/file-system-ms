package notes.project.filesystem.it;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import notes.project.filesystem.FileSystemApplication;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.utils.TestDataConstants;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import org.testcontainers.utility.DockerImageName;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
@ActiveProfiles("it")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
@RunWith(SpringRunner.class)
@AutoConfigureTestEntityManager
public abstract class AbstractIntegrationTest {

    @Inject
    protected ApplicationProperties applicationProperties;
    @Inject
    protected ObjectMapper objectMapper;

    @ClassRule
    public static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
        new PostgreSQLContainer<>(DockerImageName.parse("postgres"));
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            POSTGRES_CONTAINER.start();
            TestPropertyValues.of(
                "spring.datasource.url:" + POSTGRES_CONTAINER.getJdbcUrl(),
                "spring.datasource.username:" + POSTGRES_CONTAINER.getUsername(),
                "spring.datasource.password:" + POSTGRES_CONTAINER.getPassword()
            ).applyTo(applicationContext);
        }
    }

    @Inject
    protected TestEntityManager testEntityManager;

    protected Cluster findClusterByTitle(String title) {
        return testEntityManager.getEntityManager().createQuery(
            "select c from clusters c where c.title = :title",
            Cluster.class
        ).setParameter("title", title).getSingleResult();
    }

    protected void createCluster(Cluster cluster) throws IOException {
        Files.createDirectories(Path.of(applicationProperties.getRoot() + "/" + cluster.getExternalId().toString()));
    }

    protected void createDirectoryForFile() throws IOException {
        Files.createDirectories(
            Path.of(
                applicationProperties.getRoot() + "/" + TestDataConstants.CREATED_CLUSTER_EXTERNAL_ID + "/" + TestDataConstants.DIRECTORY_EXTERNAL_ID
            )
        );
    }


    @BeforeEach
    protected void createTestRoot() throws IOException {
        Files.createDirectories(Path.of(applicationProperties.getRoot()));
    }


    @AfterEach
    protected void deleteTestRoot() throws IOException {
        FileUtils.deleteDirectory(new File(applicationProperties.getRoot()));
    }
}
