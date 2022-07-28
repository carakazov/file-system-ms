package notes.project.filesystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Запрос на обновление файла")
public class UpdateFileRequestDto {
    @ApiModelProperty(value = "Новое содержание файла")
    private String content;
}
