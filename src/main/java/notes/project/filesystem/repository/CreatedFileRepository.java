package notes.project.filesystem.repository;

import notes.project.filesystem.model.CreatedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatedFileRepository extends JpaRepository<CreatedFile, Long> {
}
