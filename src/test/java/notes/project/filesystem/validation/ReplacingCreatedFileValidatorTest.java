package notes.project.filesystem.validation;

import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.validation.dto.ReplaceCreatedFileValidationDto;
import notes.project.filesystem.validation.impl.ReplaceCreatedFileValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReplacingCreatedFileValidatorTest {
    private Validator<ReplaceCreatedFileValidationDto> validator;

    @BeforeEach
    void init() {
        validator = new ReplaceCreatedFileValidator();
    }

    @Test
    void validateSuccess() {
        ReplaceCreatedFileValidationDto source = ApiUtils.replaceCreatedFileValidationDto();

        assertDoesNotThrow(() -> validator.validate(source));
    }

    @Test
    void validateThrow() {
        ReplaceCreatedFileValidationDto source = ApiUtils.replaceCreatedFileValidationDto();
        source.setDirectory(DbUtils.directory());

        assertThrows(
            ValidationException.class,
            () -> validator.validate(source)
        );
    }
}
