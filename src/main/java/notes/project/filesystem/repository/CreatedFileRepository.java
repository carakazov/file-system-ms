package notes.project.filesystem.repository;

import java.util.List;

import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatedFileRepository extends JpaRepository<CreatedFile, Long> {
    List<CreatedFile> findByDirectory(Directory directory);
}
