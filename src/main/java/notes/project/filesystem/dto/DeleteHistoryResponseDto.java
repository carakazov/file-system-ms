package notes.project.filesystem.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class DeleteHistoryResponseDto {
    private List<DeleteHistoryDto> history;
}
