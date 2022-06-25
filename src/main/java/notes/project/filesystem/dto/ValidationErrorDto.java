package notes.project.filesystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class ValidationErrorDto {
    private List<ErrorDto> validationErrors;
}
