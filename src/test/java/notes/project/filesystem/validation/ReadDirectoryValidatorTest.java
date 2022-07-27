package notes.project.filesystem.validation;

import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.validation.impl.ReadDirectoryValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReadDirectoryValidatorTest {
    private BusinessValidator<Directory> validator;

    @BeforeEach
    void init() {
        validator = new ReadDirectoryValidator();
    }

    @Test
    void validateSuccess() {
        Directory directory = DbUtils.directory();

        assertDoesNotThrow(() -> validator.validate(directory));
    }

    @Test
    void validateThrow() {
        Directory directory = DbUtils.directory();

        directory.setDeleted(Boolean.TRUE);

        assertThrows(
            ResourceNotFoundException.class,
            () -> validator.validate(directory)
        );
    }
}
