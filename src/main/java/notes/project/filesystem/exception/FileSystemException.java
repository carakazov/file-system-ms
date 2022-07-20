package notes.project.filesystem.exception;


import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class FileSystemException extends RuntimeException {
    @Getter
    private final ExceptionCode code;


    public FileSystemException(ExceptionCode code, String sourceMessage) {
        super(sourceMessage);
        this.code = code;
    }

    public FileSystemException(ExceptionCode code) {
        super();
        this.code = code;
    }
}
