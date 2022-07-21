package notes.project.filesystem.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Ответ на успешное создание директории")
public class DirectoryCreationResponseDto {
    @ApiModelProperty(value = "Название кластера")
    private String clusterName;
    @ApiModelProperty(value = "Название директории")
    private String directoryName;
    @ApiModelProperty(value = "Внешний ID директории")
    private UUID externalId;
    @ApiModelProperty(value = "Дата создания")
    private LocalDateTime creationDate;
}
