package dev.vality.cm.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimPageSearchParameters {

    private int page;

    private int limit;

}
