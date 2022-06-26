package notes.project.filesystem.helpers;


import notes.project.filesystem.dto.ErrorDto;
import notes.project.filesystem.dto.ValidationErrorDto;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.ApplicationPropertiesUtils;
import notes.project.filesystem.utils.ErrorHelper;
import notes.project.filesystem.utils.impl.ErrorHelperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorHelperTest {
    private ErrorHelper helper;

    @BeforeEach
    void init() {
        helper = new ErrorHelperImpl(ApplicationPropertiesUtils.applicationPropertiesForErrorHelper());
    }

    @Test
    void fromFileSystemExceptionSuccess() {
        FileSystemException exception = ApiUtils.fileSystemException();
        ErrorDto expected = ApiUtils.errorDto();
        ErrorDto actual = helper.from(exception);
        assertEquals(expected, actual);
    }

    @Test
    void fromValidationExceptionSuccess() {
        ValidationException validationException = ApiUtils.validationException();
        ValidationErrorDto expected = ApiUtils.validationErrorDto();
        ValidationErrorDto actual = helper.from(validationException);
        assertEquals(expected.getValidationErrors(), actual.getValidationErrors());
    }
}
