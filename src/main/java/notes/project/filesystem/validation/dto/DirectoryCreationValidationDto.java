package notes.project.filesystem.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class DirectoryCreationValidationDto {
    private Boolean clusterExists;
    private Boolean directoryExists;
}
