package notes.project.filesystem.file.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.file.RootInitializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Slf4j
@RequiredArgsConstructor
public class RootInitializerImpl implements RootInitializer {
    private final ApplicationProperties properties;
    private final ApplicationContext applicationContext;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        try {
            Files.createDirectories(Path.of(properties.getRoot()));
            log.info("Root directory named {} successfully created", properties.getRoot());
        } catch (IOException e) {
            log.error("Application stopped with error: {}", e.getMessage());
            SpringApplication.exit(applicationContext, () -> -1);
        }
    }
}
