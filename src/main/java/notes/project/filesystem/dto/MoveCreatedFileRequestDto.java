package notes.project.filesystem.dto;

import java.util.UUID;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на перемещение файла в другую директорию")
public class MoveCreatedFileRequestDto {
    @NotNull
    @ApiModelProperty(value = "Внешний ID файла")
    private UUID createdFileExternalId;
    @NotNull
    @ApiModelProperty(value = "Внешний ID конечной директории")
    private UUID newDirectoryExternalId;
}
