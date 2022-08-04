package notes.project.filesystem.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import notes.project.filesystem.model.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClusterRepository extends JpaRepository<Cluster, Long> {
    Optional<Cluster> findByExternalId(UUID clusterExternalId);

    Optional<Cluster> findByExternalIdAndDeletedFalse(UUID externalId);

    List<Cluster> findAllByDeletedFalse();

    List<Cluster> findAllByDeletedTrue();
}
