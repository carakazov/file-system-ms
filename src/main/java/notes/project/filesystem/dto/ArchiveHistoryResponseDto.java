package notes.project.filesystem.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Ответ на запрос истории обновлений файла")
public class ArchiveHistoryResponseDto {
    @ApiModelProperty(value = "Описание файла")
    private ArchiveCreatedFileRequestDto file;
    @ApiModelProperty(value = "Список обновлений")
    private List<ArchiveDto> history;
}
