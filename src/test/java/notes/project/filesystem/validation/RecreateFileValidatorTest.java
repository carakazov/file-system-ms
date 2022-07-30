package notes.project.filesystem.validation;

import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.validation.impl.RecreateFileValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecreateFileValidatorTest {
    private Validator<CreatedFile> validator;

    @BeforeEach
    void init() {
        validator = new RecreateFileValidator();
    }

    @Test
    void validateSuccess() {
        CreatedFile createdFile = DbUtils.createdFile();
        createdFile.setDeleted(Boolean.TRUE);

        assertDoesNotThrow(() -> validator.validate(createdFile));
    }

    @Test
    void validateThrow() {
        CreatedFile createdFile = DbUtils.createdFile();

        assertThrows(
            ValidationException.class,
            () -> validator.validate(createdFile)
        );
    }
}
