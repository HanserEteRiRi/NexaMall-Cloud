package quick.pager.shop.user.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import quick.pager.shop.user.client.UserAccountClient;

@Component
public class UserAccountFallbackFactory implements FallbackFactory<UserAccountClient> {
    @Override
    public UserAccountClient create(Throwable cause) {
        return null;
    }
}
