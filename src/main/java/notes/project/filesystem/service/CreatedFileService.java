package notes.project.filesystem.service;

import java.util.UUID;

import notes.project.filesystem.dto.*;
import notes.project.filesystem.model.CreatedFile;

public interface CreatedFileService {
    AddFileResponseDto addFile(AddFileRequestDto request);

    void deleteCreatedFile(UUID fileExternalId);

    CreatedFile findFileByExternalId(UUID externalId);

    ReadCreatedFileDto readFile(UUID externalId);

    MoveCreatedFileResponseDto moveFile(MoveCreatedFileRequestDto request);

    CreatedFile findNotDeletedFileByExternalId(UUID externalId);

    void updateFile(UUID externalId, UpdateFileRequestDto request);

    DeleteHistoryResponseDto getFileDeleteHistory(UUID fileExternalId);

    ReplacingHistoryResponseDto getReplacingHistory(UUID fileExternalId);

    ArchiveHistoryResponseDto getArchiveHistory(UUID fileExternalId);
}
