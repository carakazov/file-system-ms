package notes.project.filesystem.dto;


import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReplacingHistoryResponseDto {
    private ReplacingHistoryCreatedFileDto file;
    private List<ReplacingHistoryDto> history;
}
