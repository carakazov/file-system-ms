package notes.project.filesystem.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@ApiModel(description = "Ответ об успешно созданном файле")
@Accessors(chain = true)
public class AddFileResponseDto {
    @ApiModelProperty(value = "Название файла")
    private String title;
    @ApiModelProperty(value = "Внешний ID файла")
    private UUID externalId;
    @ApiModelProperty(value = "Время создания файла")
    private LocalDateTime createdDate;
}
