package notes.project.filesystem.utils;

import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ValidationHelper {
    private final List<ExceptionCode> codes = new ArrayList<>();

    public static ValidationHelper getInstance() {
        return new ValidationHelper();
    }

    public <T> ValidationHelper validate(T target, Predicate<T> rule, ExceptionCode code) {
        if(!rule.test(target)) {
            codes.add(code);
        }
        return this;
    }

    public void throwIfNotValid() {
        if(!codes.isEmpty()) {
            ValidationException validationException = new ValidationException();
            codes.forEach(validationException::addCode);
            throw validationException;
        }
    }
}
