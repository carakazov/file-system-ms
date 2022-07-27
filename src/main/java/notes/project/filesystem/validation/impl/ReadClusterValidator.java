package notes.project.filesystem.validation.impl;


import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.validation.BusinessValidator;
import org.springframework.stereotype.Component;

@Component
public class ReadClusterValidator implements BusinessValidator<Cluster> {
    @Override
    public void validate(Cluster target) {
        if(Boolean.TRUE.equals(target.getDeleted())) {
            throw new ResourceNotFoundException(ExceptionCode.CLUSTER_DOES_NOT_EXIST);
        }
    }
}
