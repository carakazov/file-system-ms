package notes.project.filesystem.validation;

import notes.project.filesystem.dto.AddFileRequestDto;
import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.validation.impl.FileCreationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileCreationValidatorTest {
    private Validator<AddFileRequestDto> validator;

    @BeforeEach
    void init() {
        validator = new FileCreationValidator();
    }

    @Test
    void validateSuccess() {
        AddFileRequestDto request = ApiUtils.addFileRequestDto();

        assertDoesNotThrow(
            () -> validator.validate(request)
        );
    }

    @Test
    void validateThrowWhenIncorrectTitleLength() {
        AddFileRequestDto request = ApiUtils.addFileRequestDto();

        request.setTitle("");

        assertThrows(
            ValidationException.class,
            () -> validator.validate(request)
        );
    }

    @Test
    void validateThrowWhenIncorrectContentLength() {
        AddFileRequestDto request = ApiUtils.addFileRequestDto();

        request.setContent("");

        assertThrows(
            ValidationException.class,
            () -> validator.validate(request)
        );
    }
}
