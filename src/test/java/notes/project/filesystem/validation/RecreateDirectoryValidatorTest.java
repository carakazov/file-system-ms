package notes.project.filesystem.validation;


import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.validation.impl.RecreateDirectoryValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RecreateDirectoryValidatorTest {
    private Validator<Directory> validator;

    @BeforeEach
    void init() {
        validator = new RecreateDirectoryValidator();
    }

    @Test
    void validateSuccess() {
        Directory directory = DbUtils.directory();
        directory.setDeleted(Boolean.TRUE);

        assertDoesNotThrow(() -> validator.validate(directory));
    }

    @Test
    void validateThrow() {
        Directory directory = DbUtils.directory();

        assertThrows(
            ValidationException.class,
            () -> validator.validate(directory)
        );
    }
}
