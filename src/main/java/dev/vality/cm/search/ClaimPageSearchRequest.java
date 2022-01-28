package dev.vality.cm.search;

import dev.vality.cm.model.ClaimStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClaimPageSearchRequest {

    private String partyId;

    private Long claimId;

    private String email;

    private List<ClaimStatusEnum> statuses;

}
