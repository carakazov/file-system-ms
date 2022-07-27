package notes.project.filesystem.validation;

import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.validation.impl.ReadClusterValidator;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReadClusterValidatorTest {
    private BusinessValidator<Cluster> validator;

    @BeforeEach
    void init() {
        validator = new ReadClusterValidator();
    }

    @Test
    void validateSuccess() {
        Cluster cluster = DbUtils.cluster();

        assertDoesNotThrow(() -> validator.validate(cluster));
    }

    @Test
    void validateThrow() {
        Cluster cluster = DbUtils.cluster();

        cluster.setDeleted(Boolean.TRUE);

        assertThrows(
            ResourceNotFoundException.class,
            () -> validator.validate(cluster)
        );
    }
}
