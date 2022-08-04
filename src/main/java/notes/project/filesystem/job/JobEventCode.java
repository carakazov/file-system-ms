package notes.project.filesystem.job;

import lombok.Getter;

public enum JobEventCode {
    CLUSTER_WAS_ARCHIVED("clusterWasArchived"),
    CLUSTER_WILL_BE_DELETED_SOON("clusterWillBeDeletedSoon"),
    CLUSTER_IRREVOCABLE_DELETED("clusterIrrevocableDeleted");

    @Getter
    private final String code;

    JobEventCode(String code) {
        this.code = code;
    }
}
