package notes.project.filesystem.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.ReplacingHistoryResponseDto;
import notes.project.filesystem.mapper.AddReplacingHistoryMapper;
import notes.project.filesystem.mapper.ReplacingHistoryResponseMapper;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.ReplacingHistory;
import notes.project.filesystem.repository.ReplacingHistoryRepository;
import notes.project.filesystem.service.ReplacingHistoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplacingHistoryServiceImpl implements ReplacingHistoryService {
    private final ReplacingHistoryRepository repository;
    private final AddReplacingHistoryMapper addReplacingHistoryMapper;
    private final ReplacingHistoryResponseMapper replacingHistoryResponseMapper;
    @Override
    public ReplacingHistory create(CreatedFile createdFile, Directory newDirectory) {
        ReplacingHistory replacingHistory = addReplacingHistoryMapper.to(createdFile, newDirectory);
        return repository.save(replacingHistory);
    }

    @Override
    public ReplacingHistoryResponseDto getReplacingHistory(CreatedFile createdFile) {
        List<ReplacingHistory> history = repository.findByCreatedFile(createdFile);
        return replacingHistoryResponseMapper.to(createdFile, history);
    }
}
