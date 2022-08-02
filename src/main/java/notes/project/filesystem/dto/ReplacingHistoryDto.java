package notes.project.filesystem.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReplacingHistoryDto {
    private ReplacingHistoryDirectoryDto sourceDirectory;
    private ReplacingHistoryDirectoryDto targetDirectory;
    private LocalDateTime replacingDate;
}
