package notes.project.filesystem.exception;

import lombok.Getter;

import java.util.*;

public class ValidationException extends RuntimeException {
    private final List<ExceptionCode> codes = new ArrayList<>();


    public void addCode(ExceptionCode code) {
        codes.add(code);
    }

    public List<ExceptionCode> getCodes() {
        return new ArrayList<>(codes);
    }


}
