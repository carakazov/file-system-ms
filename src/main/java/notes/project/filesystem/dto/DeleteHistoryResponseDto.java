package notes.project.filesystem.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.filesystem.model.EventType;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DeleteHistoryResponseDto {
    private String objectTitle;
    private LocalDateTime createdDate;
    private EventType currentState;
    private List<DeleteHistoryDto> history;
}
