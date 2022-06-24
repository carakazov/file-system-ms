package notes.project.filesystem.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity(name = "created_files")
public class CreatedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID externalId = UUID.randomUUID();

    private String title;

    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "directory_id")
    private Directory directory;
}
