package dev.vality.cm.converter;

import dev.vality.cm.model.UserInfoModel;
import dev.vality.cm.model.UserTypeEnum;
import dev.vality.damsel.claim_management.UserInfo;
import dev.vality.damsel.claim_management.UserType;
import org.springframework.stereotype.Component;

@Component
public class UserInfoToUserInfoModelConverter implements ClaimConverter<UserInfo, UserInfoModel> {
    @Override
    public UserInfoModel convert(UserInfo userInfo) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserId(userInfo.getId());
        userInfoModel.setEmail(userInfo.getEmail());
        userInfoModel.setUsername(userInfo.getUsername());
        userInfoModel.setType(toUserType(userInfo.getType()));
        return userInfoModel;
    }

    private UserTypeEnum toUserType(UserType userType) {
        switch (userType.getSetField()) {
            case EXTERNAL_USER:
                return UserTypeEnum.external;
            case INTERNAL_USER:
                return UserTypeEnum.internal;
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", userType));
        }
    }
}
