package notes.project.filesystem.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Информация о кластере")
public class ReadClusterDto {
    @ApiModelProperty(value = "Название кластера")
    private String title;
    @ApiModelProperty(value = "Внешний ID кластера")
    private UUID externalId;
    @ApiModelProperty(value = "Дата создания кластера")
    private LocalDateTime creationDate;
    @ApiModelProperty(value = "Список папок кластера")
    private List<DirectoryInfoDto> directories;
}
