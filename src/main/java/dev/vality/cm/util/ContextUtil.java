package dev.vality.cm.util;

import dev.vality.cm.meta.UserIdentityEmailExtensionKit;
import dev.vality.cm.meta.UserIdentityIdExtensionKit;
import dev.vality.cm.meta.UserIdentityRealmExtensionKit;
import dev.vality.cm.meta.UserIdentityUsernameExtensionKit;
import dev.vality.cm.model.UserInfoModel;
import dev.vality.damsel.claim_management.ExternalUser;
import dev.vality.damsel.claim_management.InternalUser;
import dev.vality.damsel.claim_management.UserInfo;
import dev.vality.damsel.claim_management.UserType;
import dev.vality.woody.api.trace.ContextUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContextUtil {

    public static UserInfoModel getUserInfoFromContext() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel
                .setUserId(ContextUtils.getCustomMetadataValue(UserIdentityIdExtensionKit.INSTANCE.getExtension()));
        userInfoModel.setUsername(
                ContextUtils.getCustomMetadataValue(UserIdentityUsernameExtensionKit.INSTANCE.getExtension()));
        userInfoModel
                .setEmail(ContextUtils.getCustomMetadataValue(UserIdentityEmailExtensionKit.INSTANCE.getExtension()));
        userInfoModel
                .setType(ContextUtils.getCustomMetadataValue(UserIdentityRealmExtensionKit.INSTANCE.getExtension()));
        return userInfoModel;
    }

    public static UserInfo getUserInfo() {
        return new UserInfo()
                .setId(ContextUtils.getCustomMetadataValue(UserIdentityIdExtensionKit.INSTANCE.getExtension()))
                .setEmail(ContextUtils.getCustomMetadataValue(UserIdentityEmailExtensionKit.INSTANCE.getExtension()))
                .setUsername(
                        ContextUtils.getCustomMetadataValue(UserIdentityUsernameExtensionKit.INSTANCE.getExtension()))
                .setType(getUserType());
    }

    public static void addUserInfoToContext(UserInfoModel userInfoModel) {
        ContextUtils.setCustomMetadataValue(UserIdentityIdExtensionKit.KEY, userInfoModel.getUserId());
        ContextUtils.setCustomMetadataValue(UserIdentityEmailExtensionKit.KEY, userInfoModel.getEmail());
        ContextUtils.setCustomMetadataValue(UserIdentityUsernameExtensionKit.KEY, userInfoModel.getUsername());
        ContextUtils.setCustomMetadataValue(UserIdentityRealmExtensionKit.KEY, userInfoModel.getType());
    }

    private static UserType getUserType() {
        switch (ContextUtils.getCustomMetadataValue(UserIdentityRealmExtensionKit.INSTANCE.getExtension())) {
            case external:
                return UserType.external_user(new ExternalUser());
            case internal:
                return UserType.internal_user(new InternalUser());
            default:
                throw new IllegalArgumentException();
        }
    }
}
