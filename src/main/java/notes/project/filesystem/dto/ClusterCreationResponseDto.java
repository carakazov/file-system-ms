package notes.project.filesystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
@ApiModel(description = "Информация по созданному кластеру")
public class ClusterCreationResponseDto {
    @ApiModelProperty(value = "Название созданного кластера")
    private String title;
    @ApiModelProperty(value = "Внешний ID созданного кластера")
    private UUID externalId;
    @ApiModelProperty(value = "Дата создания нового кластера")
    private LocalDateTime createDate;
}
