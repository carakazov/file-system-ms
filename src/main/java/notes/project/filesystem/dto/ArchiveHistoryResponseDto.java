package notes.project.filesystem.dto;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ArchiveHistoryResponseDto {
    private ArchiveCreatedFileRequestDto file;
    private List<ArchiveDto> history;
}
