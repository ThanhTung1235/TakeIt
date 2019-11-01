package com.takeIt.service.gift;

import com.takeIt.entity.Gift;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface GiftService {
    Gift store(Gift gift);

    Gift update(long id, Gift gift);

    Page<Gift> giftssWithPaginate(Specification specification, int page, int limit);

    Page<Gift> getGiftByCategoryId(long id, int status, int page, int limit);

    Gift getProduct(long id);

    boolean delete(long id);
}
