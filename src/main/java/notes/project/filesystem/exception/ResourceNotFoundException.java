package notes.project.filesystem.exception;

import lombok.Getter;

public class ResourceNotFoundException extends RuntimeException {
    @Getter
    private final ExceptionCode code;

    public ResourceNotFoundException(ExceptionCode code) {
        super();
        this.code = code;
    }
}
