package notes.project.filesystem.validation;

import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.validation.impl.ClusterCreationValidator;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateClusterValidatorTest {
    private Validator<ClusterCreationRequestDto> validator;

    @BeforeEach
    void init() {
        validator = new ClusterCreationValidator();
    }

    @Test
    void validateSuccess() {
        ClusterCreationRequestDto request = ApiUtils.clusterCreationRequestDto();

        assertDoesNotThrow(
            () -> validator.validate(request)
        );
    }

    @Test
    void validateThrow() {
        ClusterCreationRequestDto request = ApiUtils.clusterCreationRequestDto();

        request.setClusterTitle("");

        assertThrows(
            ValidationException.class,
            () -> validator.validate(request)
        );
    }
}
