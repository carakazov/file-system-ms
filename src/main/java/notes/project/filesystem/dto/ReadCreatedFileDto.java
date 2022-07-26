package notes.project.filesystem.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@ApiModel(description = "Представление сохраннеого файла")
@Accessors(chain = true)
public class ReadCreatedFileDto {
    @ApiModelProperty(value = "Название файла")
    private String title;
    @ApiModelProperty(value = "Содержание файла")
    private String content;
    @ApiModelProperty(value = "Дата создания файла")
    private LocalDateTime creationDate;
}
