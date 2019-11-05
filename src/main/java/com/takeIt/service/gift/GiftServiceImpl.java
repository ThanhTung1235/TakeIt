package com.takeIt.service.gift;

import com.takeIt.entity.Gift;
import com.takeIt.repository.GiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class GiftServiceImpl implements GiftService {
    @Autowired
    GiftRepository giftRepository;

    @Override
    public Gift store(Gift gift) {
        gift.setCreatedAt(Calendar.getInstance().getTimeInMillis());
        gift.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
        return giftRepository.save(gift);
    }

    @Override
    public Gift update(long id, Gift gift) {
//        product.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
        Optional<Gift> p = giftRepository.findById(id);
        if (p.isPresent()) {
            Gift giftExist = p.get();
            giftExist.setName(gift.getName());
            giftExist.setDescription(gift.getDescription());
            giftExist.setGender(gift.getGender());
            giftExist.setCity(gift.getCity());
            giftExist.setDistrict(gift.getDistrict());
            gift.setStreet_name(gift.getStreet_name());
            gift.setCategory(gift.getCategory());
        }
        return null;
    }

    @Override
    public Page<Gift> giftssWithPaginate(Specification specification, int page, int limit) {
        return giftRepository.findAll(specification, PageRequest.of(page - 1, limit));
    }

    @Override
    public Page<Gift> getGiftByCategoryId(long id, int status, int page, int limit) {
        return giftRepository.findByCategory_IdAndStatus(id, status, PageRequest.of(page - 1, limit));
    }

    @Override
    public Gift getProduct(long id) {
        return giftRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(long id) {
        Optional<Gift> p = giftRepository.findById(id);
        if (p.isPresent()) {
            Gift g = p.get();
            g.setStatus(Gift.Status.DELETED);
            g.setDeletedAt(Calendar.getInstance().getTimeInMillis());
            giftRepository.save(g);
            return true;
        }
        return false;
    }
}
