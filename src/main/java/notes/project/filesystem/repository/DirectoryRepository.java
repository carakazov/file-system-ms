package notes.project.filesystem.repository;

import java.util.Optional;
import java.util.UUID;

import net.bytebuddy.description.NamedElement;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {
    Optional<Directory> findByExternalId(UUID externalId);

    Optional<Directory> findByExternalIdAndDeletedFalse(UUID externalId);
}
