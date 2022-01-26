package dev.vality.cm.converter;

import dev.vality.cm.model.LegalAgreementModel;
import dev.vality.damsel.domain.LegalAgreement;
import dev.vality.geck.common.util.TypeUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LegalAgreementModelToLegalAgreementConverter
        implements ClaimConverter<LegalAgreementModel, LegalAgreement> {
    @Override
    public LegalAgreement convert(LegalAgreementModel legalAgreementModel) {
        return new LegalAgreement()
                .setLegalAgreementId(legalAgreementModel.getLegalAgreementId())
                .setSignedAt(TypeUtil.temporalToString(legalAgreementModel.getSignedAt()))
                .setValidUntil(
                        Optional.ofNullable(legalAgreementModel.getValidUntil())
                                .map(TypeUtil::temporalToString)
                                .orElse(null)
                );
    }
}
