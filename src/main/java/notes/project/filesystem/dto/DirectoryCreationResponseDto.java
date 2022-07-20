package notes.project.filesystem.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Ответ на успешное создание директории")
public class DirectoryCreationResponseDto {
    private String clusterName;
    private String directoryName;
    private UUID externalId;
    private LocalDateTime creationDate;
}
