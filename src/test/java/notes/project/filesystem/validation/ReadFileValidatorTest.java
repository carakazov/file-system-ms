package notes.project.filesystem.validation;

import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.validation.impl.ReadFileValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReadFileValidatorTest {
    private BusinessValidator<CreatedFile> validator;

    @BeforeEach
    void init() {
        validator = new ReadFileValidator();
    }

    @Test
    void validateSuccess() {
        CreatedFile createdFile = new CreatedFile();

        assertDoesNotThrow(() -> validator.validate(createdFile));
    }

    @Test
    void validateThrow() {
        CreatedFile createdFile = new CreatedFile();

        createdFile.setDeleted(Boolean.TRUE);

        assertThrows(
            ResourceNotFoundException.class,
            () -> validator.validate(createdFile)
        );
    }
}
