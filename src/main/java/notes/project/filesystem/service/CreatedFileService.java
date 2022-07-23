package notes.project.filesystem.service;

import java.util.List;

import notes.project.filesystem.dto.AddFileRequestDto;
import notes.project.filesystem.dto.AddFileResponseDto;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface CreatedFileService {
    AddFileResponseDto addFile(AddFileRequestDto request);
}
