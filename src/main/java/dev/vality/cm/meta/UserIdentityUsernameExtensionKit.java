package dev.vality.cm.meta;

public class UserIdentityUsernameExtensionKit extends AbstractUserIdentityExtensionKit {

    public static final String KEY = "user-identity.username";

    public static final UserIdentityUsernameExtensionKit INSTANCE = new UserIdentityUsernameExtensionKit();

    public UserIdentityUsernameExtensionKit() {
        super(KEY);
    }
}
