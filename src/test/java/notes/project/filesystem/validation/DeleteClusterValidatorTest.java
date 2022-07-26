package notes.project.filesystem.validation;

import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.validation.impl.DeleteClusterValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteClusterValidatorTest {
    private Validator<Cluster> validator;

    @BeforeEach
    void init() {
        validator = new DeleteClusterValidator();
    }

    @Test
    void validateSuccess() {
        Cluster cluster = DbUtils.cluster();

        assertDoesNotThrow(() -> validator.validate(cluster));
    }

    @Test
    void validateThrows() {
        Cluster cluster = DbUtils.cluster();

        cluster.setDeleted(Boolean.TRUE);

        assertThrows(
            ValidationException.class,
            () -> validator.validate(cluster)
        );
    }
}
