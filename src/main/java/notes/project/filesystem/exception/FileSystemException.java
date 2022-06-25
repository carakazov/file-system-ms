package notes.project.filesystem.exception;


import lombok.Getter;

public class FileSystemException extends RuntimeException {
    @Getter
    private final ExceptionCode code;

    public FileSystemException(ExceptionCode code, String sourceMessage) {
        super(sourceMessage);
        this.code = code;
    }
}
