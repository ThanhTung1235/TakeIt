package com.takeIt.service.exchangeRequest;

import com.takeIt.entity.ExchangeRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ExchangeRequestService {
    ExchangeRequest store(ExchangeRequest exchangeRequest);

    Page<ExchangeRequest> getRequestOfReceiver(long receiverId, int page, int limit);

    Page<ExchangeRequest> getRequestOfGift(long giftId, int page, int limit);
}
