package notes.project.filesystem.controller;

import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.service.logic.RecreateFacade;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recreate")
@Api("Контроллер по восстановлению объектов")
public class RecreateController {
    private final RecreateFacade recreateFacade;


    @PutMapping("/file/{id}")
    @ApiOperation(value = "Воссоздание файла")
    public void recreateFile(@PathVariable(name = "id") @ApiParam(value = "Внешний ID файла") UUID fileExternalId) {
        recreateFacade.recreateFile(fileExternalId);
    }

    @PutMapping("/directory/{id}")
    @ApiOperation(value = "Воссоздание директории")
    public void recreateDirectory(@PathVariable(name = "id") @ApiParam(value = "Внешний ID директории") UUID directoryExternalId) {
        recreateFacade.recreateDirectory(directoryExternalId);
    }

    @PutMapping("/cluster/{id}")
    @ApiOperation(value = "Воссоздание кластера")
    public void recreateCluster(@PathVariable(name = "id") @ApiParam(value = "Внешний ID кластера") UUID clusterExternalId) {
        recreateFacade.recreateCluster(clusterExternalId);
    }
}
