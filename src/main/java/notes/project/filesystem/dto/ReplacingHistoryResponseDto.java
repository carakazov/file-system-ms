package notes.project.filesystem.dto;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Ответ на запрос списка перемещений файла")
public class ReplacingHistoryResponseDto {
    @ApiModelProperty(value = "Описание файла")
    private ReplacingHistoryCreatedFileDto file;
    @ApiModelProperty(value = "Список перемещений")
    private List<ReplacingHistoryDto> history;
}
