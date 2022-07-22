package notes.project.filesystem.controller;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.ErrorDto;
import notes.project.filesystem.dto.ValidationErrorDto;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.utils.ErrorHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class WebControllerAdvice {
    private final ErrorHelper errorHelper;


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleCommonException(Exception exception) {
        ErrorDto errorDto = errorHelper.from(exception);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleNotValidArgument(MethodArgumentNotValidException exception) {
        ErrorDto errorDto = errorHelper.from(exception);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileSystemException.class)
    public ResponseEntity<ErrorDto> handleFileSystemException(FileSystemException exception) {
        ErrorDto errorDto = errorHelper.from(exception);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ErrorDto errorDto = errorHelper.from(exception);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrorDto> handleValidationError(ValidationException exception) {
        ValidationErrorDto validationErrorDto = errorHelper.from(exception);
        return new ResponseEntity<>(validationErrorDto, HttpStatus.BAD_REQUEST);
    }
}
