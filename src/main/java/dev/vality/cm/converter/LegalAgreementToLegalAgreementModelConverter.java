package dev.vality.cm.converter;

import dev.vality.cm.model.LegalAgreementModel;
import dev.vality.damsel.domain.LegalAgreement;
import dev.vality.geck.common.util.TypeUtil;
import org.springframework.stereotype.Component;

@Component
public class LegalAgreementToLegalAgreementModelConverter
        implements ClaimConverter<LegalAgreement, LegalAgreementModel> {

    @Override
    public LegalAgreementModel convert(LegalAgreement legalAgreement) {
        LegalAgreementModel legalAgreementModel = new LegalAgreementModel();
        legalAgreementModel.setLegalAgreementId(legalAgreement.getLegalAgreementId());
        legalAgreementModel.setSignedAt(TypeUtil.stringToInstant(legalAgreement.getSignedAt()));
        if (legalAgreement.isSetValidUntil()) {
            legalAgreementModel.setValidUntil(TypeUtil.stringToInstant(legalAgreement.getValidUntil()));
        }
        return legalAgreementModel;
    }

}
