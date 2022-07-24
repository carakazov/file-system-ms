package notes.project.filesystem.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatedFileRepository extends JpaRepository<CreatedFile, Long> {
    Optional<CreatedFile> findByExternalId(UUID externalId);
}
