package notes.project.filesystem.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class AddFileResponseDto {
    private String title;
    private UUID externalId;
}
