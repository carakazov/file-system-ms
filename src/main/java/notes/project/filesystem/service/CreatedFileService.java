package notes.project.filesystem.service;

import java.util.List;
import java.util.UUID;

import notes.project.filesystem.dto.*;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface CreatedFileService {
    AddFileResponseDto addFile(AddFileRequestDto request);

    void deleteCreatedFile(UUID fileExternalId);

    CreatedFile findFileByExternalId(UUID externalId);

    ReadCreatedFileDto readFile(UUID externalId);

    MoveCreatedFileResponseDto moveFile(MoveCreatedFileRequestDto request);

    CreatedFile findNotDeletedFileByExternalId(UUID externalId);

    void updateFile(UUID externalId, UpdateFileRequestDto request);
}
