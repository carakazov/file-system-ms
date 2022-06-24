package notes.project.filesystem.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "replacing_history")
public class ReplacingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "source_directory_id")
    private Directory sourceDirectory;

    @ManyToOne
    @JoinColumn(name = "target_directory_id")
    private Directory targetDirectory;

    @ManyToOne
    @JoinColumn(name = "created_file_id")
    private CreatedFile createdFile;

    private LocalDateTime replacingDate;
}
