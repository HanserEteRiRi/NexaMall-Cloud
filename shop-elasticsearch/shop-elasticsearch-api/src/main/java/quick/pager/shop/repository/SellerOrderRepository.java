package quick.pager.shop.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import quick.pager.shop.model.ESSellerOrder;

public interface SellerOrderRepository extends ElasticsearchRepository<ESSellerOrder, Long> {
}
