package com.takeIt.service.gift;

import com.takeIt.entity.Gift;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;


public interface GiftService {
    Gift store(Gift gift);

    Gift update(long id, Gift gift);

    Page<Gift> giftsWithPaginatePublish(Specification specification, int page, int limit);

    Gift updateStatusGift(long id, boolean status);

    Gift getProduct(long id);

    boolean delete(long id);
}
