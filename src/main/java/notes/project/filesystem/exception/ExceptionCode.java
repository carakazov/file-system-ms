package notes.project.filesystem.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public enum ExceptionCode {
    CLUSTER_CREATION_ERROR("clusterCreationError"),
    CLUSTER_WITH_SUCH_NAME_ALREADY_EXISTS("clusterWithSuchNameAlreadyExists");
    @Getter
    private final String code;

    ExceptionCode(String code) {
        this.code = code;
    }
}
