package notes.project.filesystem.service.impl;

import java.time.LocalDateTime;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.DeleteHistory;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.EventType;
import notes.project.filesystem.repository.DeleteHistoryRepository;
import notes.project.filesystem.service.DeleteHistoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHistoryServiceImpl implements DeleteHistoryService {
    private final DeleteHistoryRepository deleteHistoryRepository;

    @Override
    @Transactional
    public void createDirectoryDeleteHistory(Directory directory) {
        DeleteHistory deleteHistory = new DeleteHistory();
        deleteHistory.setDirectory(directory);
        setDefaultParameters(deleteHistory);
        deleteHistoryRepository.save(deleteHistory);
    }

    @Override
    @Transactional
    public void createCreatedFileDeleteHistory(CreatedFile createdFile) {
        DeleteHistory deleteHistory = new DeleteHistory();
        deleteHistory.setCreatedFile(createdFile);
        setDefaultParameters(deleteHistory);
        deleteHistoryRepository.save(deleteHistory);
    }

    private void setDefaultParameters(DeleteHistory deleteHistory) {
        deleteHistory.setEvent(EventType.DELETED);
        deleteHistory.setDate(LocalDateTime.now());
    }
}
