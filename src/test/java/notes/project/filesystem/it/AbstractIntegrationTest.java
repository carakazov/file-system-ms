package notes.project.filesystem.it;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import notes.project.filesystem.FileSystemApplication;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.utils.TestAsyncTaskExecutor;
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
import org.springframework.context.ApplicationContext;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes = AbstractIntegrationTest.IntegrationTestConfiguration.class)
@AutoConfigureJsonTesters
@ActiveProfiles("it")
public abstract class AbstractIntegrationTest {

    @Inject
    protected ApplicationProperties applicationProperties;
    @Inject
    protected ObjectMapper objectMapper;


    @ActiveProfiles("it")
    @TestConfiguration
    public static class IntegrationTestConfiguration {
        @Bean("asyncTaskExecutor")
        public TaskExecutor getTaskExecutor(EntityManager entityManager) {
            return new TestAsyncTaskExecutor(entityManager);
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

    protected void createDirectoryWithFile() throws IOException {
        createDirectoryForFile();
        Files.createFile(TestDataConstants.RESOLVED_PATH_FOR_CREATE_FILE);
        try(FileOutputStream fileOutputStream = new FileOutputStream(TestDataConstants.RESOLVED_PATH_FOR_CREATE_FILE.toString())) {
            fileOutputStream.write(TestDataConstants.FILE_CONTENT.getBytes(StandardCharsets.UTF_8));
        }
    }

    protected void assertFileCreatedThenDelete(Path path) throws IOException{
        assertTrue(Files.exists(path));
        Files.delete(path);
    }

    protected void assertFileDeleted(Path path) throws IOException {
        assertFalse(Files.exists(path));
    }

    @BeforeEach
    protected void createTestRoot() throws IOException {
        Files.createDirectories(Path.of(applicationProperties.getRoot()));
        Files.createDirectories(Path.of(applicationProperties.getArchiveRoot()));
    }


    @AfterEach
    protected void deleteTestRoot() throws IOException {
        FileUtils.deleteDirectory(new File(applicationProperties.getRoot()));
        FileUtils.deleteDirectory(new File(applicationProperties.getArchiveRoot()));
    }


}
