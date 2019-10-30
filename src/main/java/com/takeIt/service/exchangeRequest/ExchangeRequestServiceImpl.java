package com.takeIt.service.exchangeRequest;

import com.takeIt.entity.ExchangeRequest;
import com.takeIt.repository.ExchangeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRequestServiceImpl implements ExchangeRequestService {
    @Autowired
    ExchangeRequestRepository exchangeRequestRepository;

    @Override
    public ExchangeRequest store(ExchangeRequest exchangeRequest) {
        return exchangeRequestRepository.save(exchangeRequest);
    }

    @Override
    public Page<ExchangeRequest> getRequestOfReceiver(long receiverId, int page, int limit) {
        return exchangeRequestRepository.findByAccount_Id(receiverId, PageRequest.of(page - 1, limit));
    }

    @Override
    public Page<ExchangeRequest> getRequestOfGift(long giftId, int page, int limit) {
        return exchangeRequestRepository.findByGift_Id(giftId, PageRequest.of(page - 1, limit));
    }
}
