package notes.project.filesystem.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "clusters")
@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
public class Cluster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private UUID externalId = UUID.randomUUID();

    @OneToMany(mappedBy = "cluster")
    private List<Directory> directories;

    private Boolean deleted = Boolean.FALSE;

    @CreatedDate
    private LocalDateTime lastRequestDate;

    @CreatedDate
    private LocalDateTime createDate;
}
