package notes.project.filesystem.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Ответ на запрос перемещения файла")
public class MoveCreatedFileResponseDto {
    @ApiModelProperty(value = "Внешний ID файла")
    private UUID createdFileExternalId;
    @ApiModelProperty(value = "Внешний ID новой папки")
    private UUID newDirectoryExternalId;
    @ApiModelProperty(value = "Дата перемещения")
    private LocalDateTime replacingDate;
}
