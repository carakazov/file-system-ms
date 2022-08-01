package notes.project.filesystem.service.impl;

import java.util.UUID;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.DeleteHistoryResponseDto;
import notes.project.filesystem.service.CreatedFileService;
import notes.project.filesystem.service.DeleteHistoryFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHistoryFacadeImpl implements DeleteHistoryFacade {
    private final CreatedFileService createdFileService;

    @Override
    @Transactional
    public DeleteHistoryResponseDto getCreatedFileHistory(UUID fileExternalId) {
        return createdFileService.getFileDeleteHistory(fileExternalId);
    }
}
