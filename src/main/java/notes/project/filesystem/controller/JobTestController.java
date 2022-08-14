package notes.project.filesystem.controller;

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
public class JobTestController {
    private final CheckClusterLastRequestDateJob checkClusterLastRequestDateJob;
    private final SendNotificationBeforeDeleteJob sendNotificationBeforeDeleteJob;
    private final DeleteClusterArchiveJob deleteClusterArchiveJob;

    @GetMapping("/check-cluster")
    public void checkClusterLastRequestDateJob() {
        checkClusterLastRequestDateJob.doJob();
    }

    @GetMapping("/send-notification")
    public void sendNotificationBeforeDeleteJob() {
        sendNotificationBeforeDeleteJob.doJob();
    }

    @GetMapping("/delete-archive")
    public void deleteClusterArchiveJob() {
        deleteClusterArchiveJob.doJob();
    }
}
