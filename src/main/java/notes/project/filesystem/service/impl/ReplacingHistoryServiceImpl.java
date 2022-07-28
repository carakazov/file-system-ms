package notes.project.filesystem.service.impl;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.mapper.ReplacingHistoryMapper;
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
    private final ReplacingHistoryMapper replacingHistoryMapper;
    @Override
    public ReplacingHistory create(CreatedFile createdFile, Directory newDirectory) {
        ReplacingHistory replacingHistory = replacingHistoryMapper.to(createdFile, newDirectory);
        return repository.save(replacingHistory);
    }
}
