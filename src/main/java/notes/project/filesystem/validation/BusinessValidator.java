package notes.project.filesystem.validation;

public interface BusinessValidator<T> {
    void validate(T target);
}
