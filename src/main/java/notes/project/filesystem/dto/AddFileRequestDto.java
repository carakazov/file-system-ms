package notes.project.filesystem.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class AddFileRequestDto {
    private UUID directoryExternalId;
    private String title;
    private String content;
}
