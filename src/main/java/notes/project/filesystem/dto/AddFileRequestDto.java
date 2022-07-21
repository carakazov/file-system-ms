package notes.project.filesystem.dto;

import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Запрос на создание файла в системе")
public class AddFileRequestDto {
    @ApiModelProperty(value = "Внешний ID директории файла", required = true)
    private UUID directoryExternalId;
    @ApiModelProperty(value = "Название файла", required = true)
    private String title;
    @ApiModelProperty(value = "Содержание файла", required = true)
    private String content;
}
