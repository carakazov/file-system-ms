package notes.project.filesystem.dto;

import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReplacingHistoryCreatedFileDto {
    private String fileTitle;
    private UUID fileExternalId;
}
