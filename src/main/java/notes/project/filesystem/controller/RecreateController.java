package notes.project.filesystem.controller;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.service.logic.RecreateFacade;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recreate")
public class RecreateController {
    private final RecreateFacade recreateFacade;


    @PutMapping("/file/{id}")
    public void recreateFile(@PathVariable(name = "id") UUID fileExternalId) {
        recreateFacade.recreateFile(fileExternalId);
    }

    @PutMapping("/directory/{id}")
    public void recreateDirectory(@PathVariable(name = "id") UUID directoryExternalId) {
        recreateFacade.recreateDirectory(directoryExternalId);
    }

    @PutMapping("/cluster/{id}")
    public void recreateCluster(@PathVariable(name = "id") UUID clusterExternalId) {
        recreateFacade.recreateCluster(clusterExternalId);
    }
}
