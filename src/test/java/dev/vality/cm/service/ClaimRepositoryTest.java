package dev.vality.cm.service;

import dev.vality.cm.AbstractIntegrationTest;
import dev.vality.cm.model.*;
import dev.vality.cm.model.comment.CommentModificationModel;
import dev.vality.cm.model.comment.CommentModificationTypeEnum;
import dev.vality.cm.model.shop.ShopLocationModificationModel;
import dev.vality.cm.model.shop.TurnoverLimitModificationModel;
import dev.vality.cm.model.shop.TurnoverLimitsModificationModel;
import dev.vality.cm.repository.ClaimRepository;
import dev.vality.cm.repository.ClaimSpecifications;
import dev.vality.cm.repository.ModificationRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ClaimRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private ModificationRepository modificationRepository;

    @Test(expected = ConstraintViolationException.class)
    public void testRequireNullSave() throws Throwable {
        ClaimModel claimModel = new ClaimModel();
        claimModel.setPartyId("party_id");
        claimModel.setClaimStatus(new ClaimStatusModel(ClaimStatusEnum.pending, null));

        ShopLocationModificationModel shopLocationModificationModel = new ShopLocationModificationModel();
        shopLocationModificationModel.setShopId("shop_id");

        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserId("213");
        userInfoModel.setEmail("qwe@qwe.qwe");
        userInfoModel.setUsername("qwe");
        userInfoModel.setType(UserTypeEnum.external);
        shopLocationModificationModel.setUserInfo(userInfoModel);
        //require non null here
        shopLocationModificationModel.setLocation(new ShopUrlLocationModel());

        claimModel.setModifications(List.of(shopLocationModificationModel));

        try {
            claimRepository.save(claimModel);
        } catch (TransactionSystemException ex) {
            throw ex.getOriginalException().getCause();
        }
    }

    @Test
    @Transactional
    public void testSearchClaimsByEmail() {
        ClaimModel claimModel = new ClaimModel();
        claimModel.setPartyId("party_id");
        claimModel.setClaimStatus(new ClaimStatusModel(ClaimStatusEnum.pending, null));
        ShopLocationModificationModel shopLocationModificationModel = new ShopLocationModificationModel();
        shopLocationModificationModel.setShopId("shop_id");
        ShopUrlLocationModel shopLocationModel = new ShopUrlLocationModel();
        shopLocationModel.setUrl("url");
        shopLocationModificationModel.setLocation(shopLocationModel);
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserId("213");
        userInfoModel.setEmail("qwe@qwe.qwe");
        userInfoModel.setUsername("qwe");
        userInfoModel.setType(UserTypeEnum.external);
        shopLocationModificationModel.setUserInfo(userInfoModel);
        claimModel.setModifications(Arrays.asList(shopLocationModificationModel));
        claimRepository.save(claimModel);

        claimModel = new ClaimModel();
        claimModel.setPartyId("party_id");
        claimModel.setClaimStatus(new ClaimStatusModel(ClaimStatusEnum.pending, null));
        CommentModificationModel commentModificationModel = new CommentModificationModel();
        commentModificationModel.setCommentId("comment_id");
        commentModificationModel.setCommentModificationType(CommentModificationTypeEnum.creation);
        userInfoModel = new UserInfoModel();
        userInfoModel.setUserId("213");
        userInfoModel.setEmail("qwe@qwe.q123");
        userInfoModel.setUsername("qwe");
        userInfoModel.setType(UserTypeEnum.external);
        commentModificationModel.setUserInfo(userInfoModel);

        CommentModificationModel secondCommentModificationModel = new CommentModificationModel();
        secondCommentModificationModel.setCommentId("comment_id_2");
        secondCommentModificationModel.setCommentModificationType(CommentModificationTypeEnum.creation);
        secondCommentModificationModel.setUserInfo(userInfoModel);
        claimModel.setModifications(Arrays.asList(commentModificationModel, secondCommentModificationModel));
        claimRepository.save(claimModel);

        List<ClaimModel> claimModels =
                claimRepository.findAll(ClaimSpecifications.equalsByEmail(userInfoModel.getEmail()));
        assertEquals(1L, claimModels.size());
        assertEquals(userInfoModel.getEmail(), claimModels.get(0).getModifications().get(0).getUserInfo().getEmail());

        claimModels = claimRepository.findAll(ClaimSpecifications.equalsByEmail(null));
        assertEquals(2L, claimModels.size());
    }

    @Test
    public void testSaveClaimWithTurnoverLimits() {
        ClaimModel claimModel = new ClaimModel();
        claimModel.setPartyId("party_id");
        claimModel.setClaimStatus(new ClaimStatusModel(ClaimStatusEnum.pending, null));

        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserId("213");
        userInfoModel.setEmail("qwe@qwe.qwe");
        userInfoModel.setUsername("qwe");
        userInfoModel.setType(UserTypeEnum.external);

        var turnoverLimitsModificationModel = new TurnoverLimitsModificationModel();

        TurnoverLimitModificationModel limitModificationModelFirst = new TurnoverLimitModificationModel();
        limitModificationModelFirst.setLimitConfigId("1");
        limitModificationModelFirst.setDomainRevision(4L);
        limitModificationModelFirst.setAmountUpperBoundary(100L);
        limitModificationModelFirst.setLimit(turnoverLimitsModificationModel);

        TurnoverLimitModificationModel limitModificationModelSecond = new TurnoverLimitModificationModel();
        limitModificationModelSecond.setLimitConfigId("2");
        limitModificationModelSecond.setDomainRevision(8L);
        limitModificationModelSecond.setAmountUpperBoundary(200L);
        limitModificationModelSecond.setLimit(turnoverLimitsModificationModel);

        var turnoverLimits = Set.of(limitModificationModelFirst, limitModificationModelSecond);

        turnoverLimitsModificationModel.setShopId("shopId");
        turnoverLimitsModificationModel.setLimits(turnoverLimits);
        turnoverLimitsModificationModel.setUserInfo(userInfoModel);

        claimModel.setModifications(List.of(turnoverLimitsModificationModel));

        claimRepository.save(claimModel);

        List<ModificationModel> modificationModels = modificationRepository.findAll();
        var models = ((TurnoverLimitsModificationModel) modificationModels.get(0)).getLimits();

        assertEquals(turnoverLimits.size(), models.size());
    }
}
