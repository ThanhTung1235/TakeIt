package com.takeIt.service.transaction;

import com.takeIt.entity.Transaction;
import com.takeIt.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    public JavaMailSender emailSender;

    @Override
    public Transaction store(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Page<Transaction> getAll(int page, int limit) {
        return transactionRepository.findAll(PageRequest.of(page - 1, limit));
    }

    @Override
    public List<Transaction> transactions() {
        return transactionRepository.findAll();
    }

    @Override
    public void sendMailConfirm(String to, String receiverName, long id, String text, String thumbnail) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Simple take");
        String confirm = "http://localhost:8080/_api/transactions/exchangeConfirm" + id + "?status=true";
        String cancel = "http://localhost:8080/_api/transactions/exchangeConfirm" + id + "?status=false";
        helper.setText("<!doctype html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\" />\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <title>Simple Transactional Email</title>" +
                "<style>\n" +
                "  .btn{\n" +
                "    width: 25%;\n" +
                "    outline: none;\n" +
                "    border: none;\n" +
                "    border-radius: 5px;\n" +
                "    color: white;\n" +
                "    padding: 5px\n" +
                "    \n" +
                "  }\n" +
                "  .text-center{\n" +
                "    text-align: center;\n" +
                "  }\n" +
                "  body{\n" +
                "    margin: 0;\n" +
                "    padding : 0;\n" +
                "    font-family: Sans-serif;  \n" +
                "  }\n" +
                "  .container{\n" +
                "    width: 90%;\n" +
                "    padding-top: 3rem;\n" +
                "    padding-right: 15px;\n" +
                "    padding-left: 15px;\n" +
                "    margin-right: auto;\n" +
                "    margin-left: auto;\n" +
                "  }\n" +
                "  .card{\n" +
                "    position: relative;\n" +
                "    flex-direction: column;\n" +
                "    min-width: 0;\n" +
                "    word-wrap: break-word;\n" +
                "    background-color: #fff;\n" +
                "    background-clip: border-box;\n" +
                "    border: 1px solid rgba(0,0,0,.125);\n" +
                "    border-radius: .25rem;\n" +
                "    padding:10px\n" +
                "  }\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>\n" +
                "<div class=\"container\">\n" +
                "  <div class=\"card\">\n" +
                "    <div class=\"card-tile\">\n" +
                "      Xin chào\n" +
                "    </div>\n" +
                "    <p>Nguoi dung'" + receiverName + "da den nhan mon hang ma ban dang chua ? '</p>\n" +
                "    <p>'" + text + "'</p>\n" +
                "        <div style=\"display:flex; justify-content: center\">\n" +
                "      <img src='" + thumbnail + "' width=\"400\">\n" +
                "    </div>\n" +
                "    <div style=\"justify-content: space-evenly;display:flex;margin-top: 20px\">\n" +
                "      <a href='" + confirm + "' class=\"btn\" style=\"background: #007bff; color: #fff\">\n" +
                "      Da giao dich xong\n" +
                "    </a>\n" +
                "    <a href='" + cancel + "' class=\"btn\" style=\"background: #dc3545 ;text-decoration: none; color: #fff\">\n" +
                "      Nguoi dung khong den nhan\n" +
                "    </a>\n" +
                "    </div>\n" +
                "<p style=\"font-size:12px\" class=\"text-center text-secondary mt-3\"> Company Email: simpletake0420@gmail.com <br> Contact: 0123456789</p>\n" +
                "  </div>\n" +
                "</div>  \n" +
                "</div>\n " +
                " </body>\n" +
                "</html>", true);
        emailSender.send(message);
    }

    @Override
    public Transaction updateStatusTransaction(long id, boolean status) {
        Optional<Transaction> optional = transactionRepository.findById(id);
        if (optional.isPresent()) {
            Transaction transaction = optional.get();
            if (status) {
                transaction.setStatus(Transaction.Status.DONE);
                transactionRepository.save(transaction);
            } else {
                transaction.setStatus(Transaction.Status.CANCEL_EXCHANGING);
                transactionRepository.save(transaction);
            }
        }
        return null;
    }
}
