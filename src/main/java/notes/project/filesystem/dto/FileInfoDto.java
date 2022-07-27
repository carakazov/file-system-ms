package notes.project.filesystem.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@ApiModel(description = "Краткая информация по файлу")
public class FileInfoDto {
    @ApiModelProperty(value = "Название файла")
    private String title;
    @ApiModelProperty(value = "Внешний ID файла")
    private UUID externalId;
    @ApiModelProperty(value = "Дата создания файла")
    private LocalDateTime creationDate;
}
