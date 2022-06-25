package notes.project.filesystem.validation;

public interface Validator<T> {
    void validate(T source);
}
