package notes.project.filesystem.utils;

import notes.project.filesystem.dto.ErrorDto;
import notes.project.filesystem.dto.ValidationErrorDto;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.exception.ValidationException;

public interface ErrorHelper {
    ErrorDto from(FileSystemException exception);
    ValidationErrorDto from(ValidationException validationException);
    ErrorDto from(Exception exception);
}
