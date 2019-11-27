package com.takeIt.service.exchangeRequest;

import com.takeIt.entity.ExchangeRequest;
import com.takeIt.entity.Gift;
import com.takeIt.entity.Transaction;
import com.takeIt.repository.ExchangeRequestRepository;
import com.takeIt.repository.GiftRepository;
import com.takeIt.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class ExchangeRequestServiceImpl implements ExchangeRequestService {
    @Autowired
    ExchangeRequestRepository exchangeRequestRepository;
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    GiftRepository giftRepository;

    @Override
    public ExchangeRequest store(ExchangeRequest exchangeRequest) {
        return exchangeRequestRepository.save(exchangeRequest);
    }

    @Override
    public Page<ExchangeRequest> getRequestOfReceiver(long receiverId, int page, int limit) {
        return exchangeRequestRepository.findByAccount_Id(receiverId, PageRequest.of(page - 1, limit));
    }

    @Override
    public Page<ExchangeRequest> getRequestOfGift(long giftId, long accountId, int page, int limit) {
        Optional<Gift> optional = giftRepository.findById(giftId);
        if (optional.isPresent()) {
            long accountExist = optional.get().getAccount().getId();
            if (accountExist == accountId) {
                return exchangeRequestRepository.findByGift_Id(giftId, PageRequest.of(page - 1, limit));
            } else return null;
        } else return null;


    }


    @Override
    public ExchangeRequest updateStatusRequest(long id, boolean status) {
        Optional<ExchangeRequest> requestOption = exchangeRequestRepository.findById(id);
        if (requestOption.isPresent()) {
            ExchangeRequest requestExist = requestOption.get();
            if (status) {
                requestExist.setStatus(ExchangeRequest.Status.CONFIRMED);
                exchangeRequestRepository.save(requestExist);
                createTransaction(requestExist);
            } else {
                requestExist.setStatus(ExchangeRequest.Status.CANCEL);
                exchangeRequestRepository.save(requestExist);
            }
            return requestExist;
        } else
            return null;
    }

    @Override
    public ExchangeRequest findByAccountIdAndGiftId(long receiverId, long giftId) {
        return exchangeRequestRepository.findByAccount_IdAndGift_Id(receiverId, giftId).orElse(null);
    }

    public void sendSimpleMessage(String to, String receiverName, long id, String text, String thumbnail) throws
            MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Simple take");
        String confirm = "http://localhost:8080/_api/exchanges/" + id + "?status=true";
        String cancel = "http://localhost:8080/_api/exchanges/" + id + "?status=false";
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
                "    <p>Nguoi dung " + receiverName + " muon den nhan mot mon do ma ban da chia se tren Simple take voi loi nhan : </p>\n" +
                "    <p>" + text + "</p>\n" +
                "        <div style=\"display:flex; justify-content: center\">\n" +
                "      <img src='" + thumbnail + "' width=\"400\">\n" +
                "    </div>\n" +
                "    <div style=\"justify-content: space-evenly;display:flex;margin-top: 20px\">\n" +
                "      <a href='" + confirm + "' class=\"btn\" style=\"background: #007bff; color: #fff\">\n" +
                "      Xac nhan\n" +
                "    </a>\n" +
                "    <a href='" + cancel + "' class=\"btn\" style=\"background: #dc3545 ;text-decoration: none; color: #fff\">\n" +
                "      Khong chap nhan\n" +
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

    public void createTransaction(ExchangeRequest exchangeRequest) {
        Transaction transaction = new Transaction();
        transaction.setAccount(exchangeRequest.getAccount());
        transaction.setGift(exchangeRequest.getGift());
        transaction.setStatus(Transaction.Status.IS_EXCHANGING);
        transaction.setExchangeRequest(exchangeRequest);

        transactionRepository.save(transaction);
//        gui lai mail cho receiver
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Simple Take message");
        mailMessage.setText("Tuyệt " + exchangeRequest.getGift().getAccount().getUsername()
                + " đã đồng ý bạn nhận món đồ "
                + exchangeRequest.getGift().getName()
                + ". Bây giờ chỉ cần thỏa thuận thời gian và cách nhận đồ nữa thôi.");
        mailMessage.setTo(exchangeRequest.getAccount().getAccountInfo().getEmail());
        emailSender.send(mailMessage);
    }

}
