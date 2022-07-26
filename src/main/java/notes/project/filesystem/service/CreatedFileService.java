package notes.project.filesystem.service;

import java.util.List;
import java.util.UUID;

import notes.project.filesystem.dto.AddFileRequestDto;
import notes.project.filesystem.dto.AddFileResponseDto;
import notes.project.filesystem.dto.ReadCreatedFileDto;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface CreatedFileService {
    AddFileResponseDto addFile(AddFileRequestDto request);

    void deleteCreatedFile(UUID fileExternalId);

    CreatedFile findFileByExternalId(UUID externalId);

    ReadCreatedFileDto readFile(UUID externalId);
}
