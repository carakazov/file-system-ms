package notes.project.filesystem.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringExclude;

@Data
@Accessors(chain = true)
@ApiModel(description = "Ответ на запрос директорий")
public class ReadDirectoryDto {
    @ApiModelProperty(value = "Название папки")
    private String title;
    @ApiModelProperty(value = "Внешний ID папки")
    private UUID externalId;
    @ApiModelProperty(value = "Дата создания директории")
    private LocalDateTime creationDate;
    @ApiModelProperty(value = "Краткое описание файлов")
    private List<FileInfoDto> files;
}
