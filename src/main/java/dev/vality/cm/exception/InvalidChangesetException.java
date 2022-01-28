package dev.vality.cm.exception;

import dev.vality.cm.model.ModificationModel;
import lombok.Getter;

import java.util.List;

@Getter
public class InvalidChangesetException extends RuntimeException {

    private final List<ModificationModel> modifications;

    private final String invalidChangesetReasonJson;

    public InvalidChangesetException(String message,
                                     List<ModificationModel> modifications,
                                     String invalidChangesetReasonJson) {
        super(message);
        this.modifications = modifications;
        this.invalidChangesetReasonJson = invalidChangesetReasonJson;
    }

}
