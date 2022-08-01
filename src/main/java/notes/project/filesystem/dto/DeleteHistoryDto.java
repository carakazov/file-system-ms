package notes.project.filesystem.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;
import notes.project.filesystem.model.EventType;

@Data
@Accessors(chain = true)
public class DeleteHistoryDto {
    private EventType event;
    private LocalDateTime eventDate;
}
