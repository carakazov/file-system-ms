package notes.project.filesystem.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ClusterCreationRequestDto {
    private String clusterTitle;
}
