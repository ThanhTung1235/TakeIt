package com.takeIt.service.transaction;

import com.takeIt.entity.ExchangeRequest;
import com.takeIt.entity.Transaction;
import org.springframework.data.domain.Page;

import javax.mail.MessagingException;
import java.util.List;

public interface TransactionService {
    Transaction store(Transaction transaction);

    Page<Transaction> getAllReceiver(long accountId,int page, int limit);

    Page<Transaction> getAllOfOwner(long accountId,int page, int limit);

    List<Transaction> transactions();

    void sendMailConfirm(String to, String receiverName, long id, String text, String thumbnail) throws MessagingException;

    Transaction updateStatusTransaction(long id, boolean status);
}
