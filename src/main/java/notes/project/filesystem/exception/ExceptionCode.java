package notes.project.filesystem.exception;

import lombok.Getter;

public enum ExceptionCode {
    CREATION_ERROR("unexpectedErrorWhileCreationOperation"),
    OBJECT_ALREADY_EXISTS("objectAlreadyExists"),
    CLUSTER_WITH_SUCH_NAME_ALREADY_EXISTS("clusterWithSuchNameAlreadyExists"),
    CLUSTER_DOES_NOT_EXISTS("clusterDoesNotExists"),
    DIRECTORY_DOES_NOT_EXISTS("directoryDoesNotExists"),
    DIRECTORY_EXISTS("directoryExists");
    @Getter
    private final String code;

    ExceptionCode(String code) {
        this.code = code;
    }
}
