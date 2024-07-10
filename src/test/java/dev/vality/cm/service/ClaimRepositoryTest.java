package dev.vality.cm.service;

import dev.vality.cm.AbstractIntegrationTest;
import dev.vality.cm.model.*;
import dev.vality.cm.model.*;
import dev.vality.cm.model.comment.CommentModificationModel;
import dev.vality.cm.model.comment.CommentModificationTypeEnum;
import dev.vality.cm.model.shop.ShopLocationModificationModel;
import dev.vality.cm.repository.ClaimRepository;
import dev.vality.cm.repository.ClaimSpecifications;
import dev.vality.cm.repository.ModificationRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClaimRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private ClaimRepository claimRepository;

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

}
