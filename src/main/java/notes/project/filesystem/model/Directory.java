package notes.project.filesystem.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "directories")
public class Directory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID externalId = UUID.randomUUID();

    private String title;

    private LocalDateTime createDate;

    private Boolean deleted;
}
