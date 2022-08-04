package notes.project.filesystem.controller;


import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.dto.DeleteHistoryResponseDto;
import notes.project.filesystem.dto.ReadClusterDto;
import notes.project.filesystem.service.logic.ClusterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cluster")
@Api(value = "Контроллер по управлению кластером")
public class ClusterController {
    private final ClusterService clusterService;

    @PostMapping
    @ApiOperation(value = "Создание кластера")
    public ClusterCreationResponseDto createCluster(@RequestBody @Validated ClusterCreationRequestDto request) {
        return clusterService.createCluster(request);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление кластера")
    public void deleteCluster(@PathVariable(name = "id") @ApiParam(name = "Внешний ID кластера") UUID externalId) {
        clusterService.deleteCluster(externalId);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Чтение кластера")
    public ReadClusterDto readCluster(@PathVariable(name = "id") @ApiParam(name = "Внешний ID кластера") UUID externalId) {
        return clusterService.readCluster(externalId);
    }

    @GetMapping("/{id}/deleteHistory")
    @ApiOperation(value = "Запрос на историй удалений и восстановлений кластера")
    public DeleteHistoryResponseDto getClusterDeleteHistory(@PathVariable(name = "id") @ApiParam(name = "Внешний ID кластера") UUID externalId) {
        return clusterService.getClusterDeleteHistory(externalId);
    }
}
