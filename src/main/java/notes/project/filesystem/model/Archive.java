package notes.project.filesystem.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "archive")
public class Archive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID versionFileGuid;

    private LocalDateTime editedDate;

    @ManyToOne
    @JoinColumn(name = "created_file_id")
    private CreatedFile createdFile;
}
