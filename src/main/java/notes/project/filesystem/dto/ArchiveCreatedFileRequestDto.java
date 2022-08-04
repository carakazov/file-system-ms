package notes.project.filesystem.dto;

import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Описание файла для истории обновлений")
public class ArchiveCreatedFileRequestDto {
    @ApiModelProperty(value = "Название файла")
    private String fileTitle;
    @ApiModelProperty(value = "Внешний ID файла")
    private UUID fileExternalId;
}
