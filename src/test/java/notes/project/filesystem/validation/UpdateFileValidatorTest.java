package notes.project.filesystem.validation;

import io.swagger.annotations.Api;
import notes.project.filesystem.dto.UpdateFileRequestDto;
import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.validation.impl.UpdateFileValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UpdateFileValidatorTest {
    private Validator<UpdateFileRequestDto> validator;

    @BeforeEach
    void init() {
        validator = new UpdateFileValidator();
    }

    @Test
    void validateSuccess() {
        UpdateFileRequestDto request = ApiUtils.updateFileRequestDto();
        assertDoesNotThrow(() -> validator.validate(request));
    }

    @Test
    void validateThrow() {
        UpdateFileRequestDto request = ApiUtils.updateFileRequestDto();

        request.setContent("");

        assertThrows(
            ValidationException.class,
            () -> validator.validate(request)
        );
    }
}
