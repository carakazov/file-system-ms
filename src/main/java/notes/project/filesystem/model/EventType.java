package notes.project.filesystem.model;

import lombok.Getter;

public enum EventType {
    DELETED(true),
    RECREATED(false);

    private final Boolean deleted;

    EventType(Boolean deleted) {
        this.deleted = deleted;
    }

    public static EventType getType(Boolean deleted) {
        if(deleted) {
            return EventType.DELETED;
        } else {
            return EventType.RECREATED;
        }
    }
}
