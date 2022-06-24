package notes.project.filesystem.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "directory_id")
    private Directory directory;

    @ManyToOne
    @JoinColumn(name = "created_file_id")
    private CreatedFile createdFile;

    @Enumerated(EnumType.STRING)
    private EventType event;

    private LocalDateTime date;
}
