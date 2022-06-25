package notes.project.filesystem.controller;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.service.ClusterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cluster")
public class ClusterController {
    private final ClusterService clusterService;

    @PostMapping
    public ClusterCreationResponseDto createCluster(@RequestBody ClusterCreationRequestDto request) {
        return clusterService.createCluster(request);
    }
}
