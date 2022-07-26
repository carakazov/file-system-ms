package notes.project.filesystem.validation;

import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.ValidationHelper;
import notes.project.filesystem.validation.impl.DeleteDirectoryValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteDirectoryValidatorTest {
    private Validator<Directory> validator;

    @BeforeEach
    void init() {
        validator = new DeleteDirectoryValidator();
    }

    @Test
    void validateSuccess() {
        Directory directory = DbUtils.directory();

        assertDoesNotThrow(() -> validator.validate(directory));
    }

    @Test
    void validateThrows() {
        Directory directory = DbUtils.directory();

        directory.setDeleted(Boolean.TRUE);

        assertThrows(
            ValidationException.class,
            () -> validator.validate(directory)
        );
    }
}
