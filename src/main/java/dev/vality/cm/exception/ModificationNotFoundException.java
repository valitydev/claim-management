package dev.vality.cm.exception;

import lombok.Getter;

@Getter
public class ModificationNotFoundException extends RuntimeException {

    private final long modificationId;

    public ModificationNotFoundException(long modificationId) {
        super();
        this.modificationId = modificationId;
    }


}
