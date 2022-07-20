package notes.project.filesystem.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "directories")
@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
public class Directory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cluster_id")
    private Cluster cluster;

    private UUID externalId = UUID.randomUUID();

    private String title;

    @CreatedDate
    private LocalDateTime createDate;

    private Boolean deleted;
}
