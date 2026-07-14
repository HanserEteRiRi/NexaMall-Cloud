package quick.pager.shop.user.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import quick.pager.shop.user.client.AddressClient;

@Component
public class AddressFallbackFactory implements FallbackFactory<AddressClient> {
    @Override
    public AddressClient create(Throwable cause) {
        return null;
    }
}
