package notes.project.filesystem.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "Краткая информация о папке")
public class DirectoryInfoDto {
    @ApiModelProperty(value = "Название папки")
    private String title;
    @ApiModelProperty(value = "Внешний ID папки")
    private UUID externalId;
    @ApiModelProperty(value = "Дата создания папки")
    private LocalDateTime creationDate;
}
