package notes.project.filesystem.model;

import lombok.Getter;

public enum FileResolution {
    TXT(".txt"),
    ZIP(".zip");

    @Getter
    private final String resolution;

    FileResolution(String resolution) {
        this.resolution = resolution;
    }
}
