package quick.pager.shop.goods.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import quick.pager.shop.goods.client.GoodsSpuClient;

@Component
public class GoodsSpuClientFallbackFactory implements FallbackFactory<GoodsSpuClient> {
    @Override
    public GoodsSpuClient create(Throwable cause) {
        return null;
    }
}
