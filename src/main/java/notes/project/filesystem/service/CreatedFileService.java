package notes.project.filesystem.service;

import notes.project.filesystem.dto.AddFileRequestDto;
import notes.project.filesystem.dto.AddFileResponseDto;

public interface CreatedFileService {
    AddFileResponseDto addFile(AddFileRequestDto request);
}
