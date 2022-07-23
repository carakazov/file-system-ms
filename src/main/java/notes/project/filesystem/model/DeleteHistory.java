package notes.project.filesystem.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "delete_history")
public class DeleteHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "directory_id")
    private Directory directory;

    @ManyToOne
    @JoinColumn(name = "created_file_id")
    private CreatedFile createdFile;

    @ManyToOne
    @JoinColumn(name = "cluster_id")
    private Cluster cluster;

    @Enumerated(EnumType.STRING)
    private EventType event;

    @Column(name = "event_date")
    private LocalDateTime date;
}
