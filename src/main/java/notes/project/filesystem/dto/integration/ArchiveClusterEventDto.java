package notes.project.filesystem.dto.integration;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveClusterEventDto {
    private String eventCode;
    private UUID clusterExternalId;
}
