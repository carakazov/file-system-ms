package notes.project.filesystem.dto.integration;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClusterWillBeDeletedSoonEventDto extends ArchiveClusterEventDto {
    private Long daysBeforeDelete;

    public ClusterWillBeDeletedSoonEventDto(String eventCode, UUID clusterExternalId, Long daysBeforeDelete) {
        super(eventCode, clusterExternalId);
        this.daysBeforeDelete = daysBeforeDelete;
    }
}
