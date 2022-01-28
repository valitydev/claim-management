package dev.vality.cm.exception;

import dev.vality.cm.model.ClaimStatusModel;
import lombok.Getter;

@Getter
public class InvalidClaimStatusException extends RuntimeException {

    private final ClaimStatusModel claimStatusModel;

    public InvalidClaimStatusException(String message, ClaimStatusModel claimStatusModel) {
        super(message);
        this.claimStatusModel = claimStatusModel;
    }
}
