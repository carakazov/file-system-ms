package notes.project.filesystem.utils;

import javax.persistence.EntityManager;

import org.springframework.core.task.TaskExecutor;

public class TestAsyncTaskExecutor implements TaskExecutor {
    private final EntityManager entityManager;

    public TestAsyncTaskExecutor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void execute(Runnable task) {
        entityManager.flush();
        entityManager.clear();
        task.run();
    }
}
