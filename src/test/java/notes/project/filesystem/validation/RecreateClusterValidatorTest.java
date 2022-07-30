package notes.project.filesystem.validation;

import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.validation.impl.RecreateClusterValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecreateClusterValidatorTest {
    private Validator<Cluster> validator;

    @BeforeEach
    void init() {
        validator = new RecreateClusterValidator();
    }

    @Test
    void validateSuccess() {
        Cluster cluster = DbUtils.cluster();
        cluster.setDeleted(Boolean.TRUE);

        assertDoesNotThrow(() -> validator.validate(cluster));
    }

    @Test
    void validateThrow() {
        Cluster cluster = DbUtils.cluster();

        assertThrows(
            ValidationException.class,
            () -> validator.validate(cluster)
        );
    }
}
