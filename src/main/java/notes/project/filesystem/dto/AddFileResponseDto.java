package notes.project.filesystem.dto;

import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Ответ об успешно созданном файле")
public class AddFileResponseDto {
    @ApiModelProperty(value = "Название файла")
    private String title;
    @ApiModelProperty(value = "Внешний ID файла")
    private UUID externalId;
}
