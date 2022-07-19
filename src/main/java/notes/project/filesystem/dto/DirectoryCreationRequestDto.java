package notes.project.filesystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на создание новой директории")
public class DirectoryCreationRequestDto {
    @ApiModelProperty(value = "Название создаваемой директории", required = true)
    private String directoryName;
    @ApiModelProperty(value = "Кластер, в котором будет новая директория", required = true)
    private String clusterName;
}
