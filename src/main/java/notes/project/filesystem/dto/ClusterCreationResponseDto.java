package notes.project.filesystem.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class ClusterCreationResponseDto {
    private String title;
    private UUID externalId;
    private LocalDateTime createDate;
}
