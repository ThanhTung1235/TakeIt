package com.takeIt.service.product;

import com.takeIt.entity.Gift;
import com.takeIt.repository.GiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public List<Gift> getAll() {
        giftRepository.findAll(PageRequest.of(1, 3));
        return giftRepository.findAllByStatus(1);
    }

    //update : ten, do tuoi, mo ta san pham, gioi tinh
    @Override
    public Gift update(long id, Gift gift) {
//        product.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
        Optional<Gift> p = giftRepository.findById(id);
        if (p.isPresent()) {
            Gift giftExist = p.get();
//            productExist.setName(product.getName());
////            productExist.setAddress(product.getAddress());
//            productExist.setDescription(product.getDescription());
//            productExist.setAge_range(product.getAge_range());
        }
        return null;
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
            g.setStatus(Gift.Status.DELETED.getValue());
            g.setDeletedAt(Calendar.getInstance().getTimeInMillis());
            giftRepository.save(g);
            return true;
        }
        return false;
    }

}
