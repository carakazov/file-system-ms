package notes.project.filesystem.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "archive")
@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
public class Archive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID versionFileGuid;

    @CreatedDate
    private LocalDateTime editedDate;

    @ManyToOne
    @JoinColumn(name = "created_file_id")
    private CreatedFile createdFile;
}
