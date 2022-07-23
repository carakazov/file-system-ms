package notes.project.filesystem.service.impl;

import java.time.LocalDateTime;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
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
        deleteHistory.setEvent(EventType.DELETED);
        deleteHistory.setDate(LocalDateTime.now());
        deleteHistoryRepository.save(deleteHistory);
    }
}
