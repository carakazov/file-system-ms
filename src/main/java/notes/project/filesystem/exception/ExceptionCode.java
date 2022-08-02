package notes.project.filesystem.exception;

import lombok.Getter;

public enum ExceptionCode {
    CREATION_ERROR("unexpectedErrorWhileCreationOperation"),
    DELETION_ERROR("deletionError"),
    READING_ERROR("unexpectedErrorWhileReadingOperation"),
    FILE_DOES_NOT_EXISTS("fileDoesNotExist"),
    DIRECTORY_DOES_NOT_EXIST("directoryDoesNotExist"),
    CLUSTER_DOES_NOT_EXIST("clusterDoesNotExist"),
    INCORRECT_TITLE_LENGTH("incorrectTitleLength"),
    INCORRECT_CONTENT_LENGTH("incorrectContentLength"),
    RESOURCE_NOT_FOUND("resourceNotFound"),
    WRONG_REQUEST_PARAMETERS("wrongRequestParameters"),
    FILE_ALREADY_DELETED("fileAlreadyDeleted"),
    DIRECTORY_ALREADY_DELETED("directoryAlreadyDeleted"),
    CLUSTER_ALREADY_DELETED("clusterAlreadyDeleted"),
    REPLACING_ERROR("replacingError"),
    UPDATING_ERROR("updatingError"),
    RECREATING_ERROR("recreatingError"),
    FILE_NOT_DELETED("fileNotDeleted"),
    DIRECTORY_NOT_DELETED("directoryNotDeleted"),
    CLUSTER_NOT_DELETED("clusterNotDeleted"),
    PARENT_OBJECT_DELETED("parentObjectDeleted"),
    SAME_DIRECTORY_REPLACING("sameDirectoryReplacing");
    @Getter
    private final String code;

    ExceptionCode(String code) {
        this.code = code;
    }
}
