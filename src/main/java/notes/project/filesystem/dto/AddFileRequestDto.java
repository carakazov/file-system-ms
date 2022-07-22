package notes.project.filesystem.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@ApiModel(description = "Запрос на создание файла в системе")
@Accessors(chain = true)
public class AddFileRequestDto {
    @NotNull
    @ApiModelProperty(value = "Внешний ID директории файла", required = true)
    private UUID directoryExternalId;
    @NotBlank
    @ApiModelProperty(value = "Название файла", required = true)
    private String title;
    @NotBlank
    @ApiModelProperty(value = "Содержание файла", required = true)
    private String content;
}
