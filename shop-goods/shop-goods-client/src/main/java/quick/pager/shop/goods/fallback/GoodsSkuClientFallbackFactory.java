package quick.pager.shop.goods.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import quick.pager.shop.goods.client.GoodsSkuClient;

@Component
public class GoodsSkuClientFallbackFactory implements FallbackFactory<GoodsSkuClient> {
    @Override
    public GoodsSkuClient create(Throwable cause) {
        return null;
    }
}
