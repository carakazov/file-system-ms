package notes.project.filesystem.validation;

import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.validation.impl.DirectoryCreationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DirectoryCreationValidatorTest {
    private Validator<DirectoryCreationRequestDto> validator;

    @BeforeEach
    void init() {
        validator = new DirectoryCreationValidator();
    }

    @Test
    void validateSuccess() {
        DirectoryCreationRequestDto request = ApiUtils.directoryCreationRequestDto();

        assertDoesNotThrow(
            () -> validator.validate(request)
        );
    }

    @Test
    void validateThrow() {
        DirectoryCreationRequestDto request = ApiUtils.directoryCreationRequestDto();

        request.setDirectoryName("");

        assertThrows(
            ValidationException.class,
            () -> validator.validate(request)
        );
    }
}
