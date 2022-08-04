package notes.project.filesystem.dto;

import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Описания файла для истории перемещений")
public class ReplacingHistoryCreatedFileDto {
    @ApiModelProperty(value = "Название файла")
    private String fileTitle;
    @ApiModelProperty(value = "Внешний ID файла")
    private UUID fileExternalId;
}
