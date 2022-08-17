package notes.project.filesystem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.job.CheckClusterLastRequestDateJob;
import notes.project.filesystem.job.DeleteClusterArchiveJob;
import notes.project.filesystem.job.SendNotificationBeforeDeleteJob;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-test")
@Api(value = "Контроллер для тестов джобы")
public class JobTestController {
    private final CheckClusterLastRequestDateJob checkClusterLastRequestDateJob;
    private final SendNotificationBeforeDeleteJob sendNotificationBeforeDeleteJob;
    private final DeleteClusterArchiveJob deleteClusterArchiveJob;

    @GetMapping("/check-cluster")
    @ApiOperation(value = "джоба по архивироваю кластера, которого не трогали месяц")
    public void checkClusterLastRequestDateJob() {
        checkClusterLastRequestDateJob.doJob();
    }

    @GetMapping("/send-notification")
    @ApiOperation(value = "джоба по отправке нотификации о том, что архивированный кластер скоро удалят")
    public void sendNotificationBeforeDeleteJob() {
        sendNotificationBeforeDeleteJob.doJob();
    }

    @GetMapping("/delete-archive")
    @ApiOperation(value = "джоба по удалению окончательному архивированных кластеров")
    public void deleteClusterArchiveJob() {
        deleteClusterArchiveJob.doJob();
    }
}
