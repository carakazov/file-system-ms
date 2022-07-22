package notes.project.filesystem.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на создание нового кластера")
public class ClusterCreationRequestDto {
    @NotBlank
    @ApiModelProperty(value = "Название нового кластера", required = true)
    private String clusterTitle;
}
