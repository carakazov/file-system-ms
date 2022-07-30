package notes.project.filesystem.validation.impl;

import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.utils.ValidationHelper;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class RecreateClusterValidator implements Validator<Cluster> {
    @Override
    public void validate(Cluster source) {
        ValidationHelper.getInstance()
            .validate(
                source.getDeleted(),
                Boolean.TRUE::equals,
                ExceptionCode.CLUSTER_NOT_DELETED
            ).throwIfNotValid();
    }
}
