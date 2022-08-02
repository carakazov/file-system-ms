package notes.project.filesystem.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ArchiveDto {
    private UUID versionFileGuid;
    private LocalDateTime editedDate;
}
