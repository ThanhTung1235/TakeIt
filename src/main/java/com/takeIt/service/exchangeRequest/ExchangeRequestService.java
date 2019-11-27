package com.takeIt.service.exchangeRequest;

import com.takeIt.entity.ExchangeRequest;
import org.springframework.data.domain.Page;

import javax.mail.MessagingException;
import java.util.List;

public interface ExchangeRequestService {
    ExchangeRequest store(ExchangeRequest exchangeRequest);

    Page<ExchangeRequest> getRequestOfReceiver(long receiverId, int page, int limit);

    Page<ExchangeRequest> getRequestOfGift(long giftId,long accountId, int page, int limit);

    void sendSimpleMessage(String to, String receiverName, long id, String text, String thumbnail) throws MessagingException;

    ExchangeRequest updateStatusRequest(long id, boolean status);

    ExchangeRequest findByAccountIdAndGiftId(long receiverId, long giftId);
}
