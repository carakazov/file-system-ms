package notes.project.filesystem.repository;

import java.util.Optional;
import java.util.UUID;

import notes.project.filesystem.model.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClusterRepository extends JpaRepository<Cluster, Long> {
    boolean existsByTitle(String title);
    Optional<Cluster> findByExternalId(UUID clusterExternalId);
}
