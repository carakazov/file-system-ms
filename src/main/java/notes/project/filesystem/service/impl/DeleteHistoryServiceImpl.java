package notes.project.filesystem.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.DeleteHistoryResponseDto;
import notes.project.filesystem.mapper.DeleteHistoryResponseMapper;
import notes.project.filesystem.model.*;
import notes.project.filesystem.repository.DeleteHistoryRepository;
import notes.project.filesystem.service.DeleteHistoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHistoryServiceImpl implements DeleteHistoryService {
    private final DeleteHistoryRepository deleteHistoryRepository;
    private final DeleteHistoryResponseMapper deleteHistoryResponseMapper;

    private static final Comparator<DeleteHistory> DELETE_HISTORY_COMPARATOR = (o1, o2) -> o2.getDate().compareTo(o1.getDate());

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

    @Override
    @Transactional
    public DeleteHistoryResponseDto getCreatedFileDeleteHistory(CreatedFile createdFile) {
        List<DeleteHistory> history = deleteHistoryRepository.findByCreatedFile(createdFile);
        history.sort(DELETE_HISTORY_COMPARATOR);
        EventType type;
        if(history.isEmpty()) {
            type = EventType.CREATED;
        } else {
            type = history.get(0).getEvent();
        }
        return deleteHistoryResponseMapper.to(createdFile, history, type);
    }

    private void setDefaultParameters(DeleteHistory deleteHistory, EventType eventType) {
        deleteHistory.setEvent(eventType);
        deleteHistory.setDate(LocalDateTime.now());
    }
}
