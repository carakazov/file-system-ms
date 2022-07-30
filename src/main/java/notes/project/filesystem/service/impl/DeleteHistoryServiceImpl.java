package notes.project.filesystem.service.impl;

import java.time.LocalDateTime;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.model.*;
import notes.project.filesystem.repository.DeleteHistoryRepository;
import notes.project.filesystem.service.DeleteHistoryService;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHistoryServiceImpl implements DeleteHistoryService {
    private final DeleteHistoryRepository deleteHistoryRepository;

    @Override
    @Transactional
    public void createDirectoryDeleteHistory(Directory directory, EventType eventType) {
        DeleteHistory deleteHistory = new DeleteHistory();
        deleteHistory.setDirectory(directory);
        setDefaultParameters(deleteHistory, eventType);
        deleteHistoryRepository.save(deleteHistory);
    }

    @Override
    @Transactional
    public void createCreatedFileDeleteHistory(CreatedFile createdFile, EventType eventType) {
        DeleteHistory deleteHistory = new DeleteHistory();
        deleteHistory.setCreatedFile(createdFile);
        setDefaultParameters(deleteHistory, eventType);
        deleteHistoryRepository.save(deleteHistory);
    }

    @Override
    @Transactional
    public void createClusterDeleteHistory(Cluster cluster, EventType eventType) {
        DeleteHistory deleteHistory = new DeleteHistory();
        deleteHistory.setCluster(cluster);
        setDefaultParameters(deleteHistory,eventType);
        deleteHistoryRepository.save(deleteHistory);
    }

    private void setDefaultParameters(DeleteHistory deleteHistory, EventType eventType) {
        deleteHistory.setEvent(eventType);
        deleteHistory.setDate(LocalDateTime.now());
    }
}
