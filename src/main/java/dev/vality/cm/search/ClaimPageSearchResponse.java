package dev.vality.cm.search;

import dev.vality.cm.model.ClaimModel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ClaimPageSearchResponse {

    private final List<ClaimModel> claims;

    private final String token;

}
