package notes.project.filesystem.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReplaceCreatedFileValidationDto {
    private CreatedFile file;
    private Directory directory;
}
