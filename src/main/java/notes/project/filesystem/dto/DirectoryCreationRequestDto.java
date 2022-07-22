package notes.project.filesystem.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на создание новой директории")
public class DirectoryCreationRequestDto {
    @NotBlank
    @ApiModelProperty(value = "Название создаваемой директории", required = true)
    private String directoryName;
    @NotNull
    @ApiModelProperty(value = "Кластер, в котором будет новая директория", required = true)
    private UUID clusterExternalId;
}
