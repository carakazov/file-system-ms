package notes.project.filesystem.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "replacing_history")
@EntityListeners(value = AuditingEntityListener.class)
@Accessors(chain = true)
public class ReplacingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_directory_id")
    private Directory sourceDirectory;

    @ManyToOne
    @JoinColumn(name = "target_directory_id")
    private Directory targetDirectory;

    @ManyToOne
    @JoinColumn(name = "created_file_id")
    private CreatedFile createdFile;

    @CreatedDate
    private LocalDateTime replacingDate;
}
