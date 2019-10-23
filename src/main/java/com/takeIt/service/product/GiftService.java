package com.takeIt.service.product;

import com.takeIt.entity.Gift;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GiftService {
    Gift store(Gift gift);

    List<Gift> getAll();

    Gift update(long id, Gift gift);

    Gift getProduct(long id);

    boolean delete(long id);
}
