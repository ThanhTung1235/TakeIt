package com.takeIt.endpoint.client;

import com.google.gson.Gson;
import com.takeIt.dto.RequestDTO;
import com.takeIt.entity.Account;
import com.takeIt.entity.AccountInfo;
import com.takeIt.entity.ExchangeRequest;
import com.takeIt.repository.GiftRepository;
import com.takeIt.rest.RESTPagination;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.account.AccountService;
import com.takeIt.service.accountInfo.AccountInfoService;
import com.takeIt.service.credential.CredentialsService;
import com.takeIt.service.exchangeRequest.ExchangeRequestService;
import com.takeIt.service.gift.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Action;
import java.util.Calendar;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class ExchangeRequestEndpoint {
    private static final String URL_PATH = "/_api/exchanges";
    @Autowired
    ExchangeRequestService requestService;
    @Autowired
    AccountInfoService infoService;
    @Autowired
    CredentialsService credentialsService;
    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.POST, value = URL_PATH)
    public ResponseEntity<Object> saveRequest(@RequestHeader("Authorization") String token, @RequestBody ExchangeRequest exchangeRequest) throws MessagingException {

        token = token.replaceAll("Bearer", "").trim();
        Account account = new Account();
        Account a = credentialsService.finByToken(token);

        exchangeRequest.getGift().getAccount();
        if (a != null) {
            account.setId(a.getId());
            exchangeRequest.setAccount(account);
            if (exchangeRequest.getAccount().getId() != exchangeRequest.getGift().getAccount().getId()) {
                AccountInfo accountInfo = infoService.getAccountInfoByAccountId(exchangeRequest.getGift().getAccount().getId());
                if (accountInfo == null) {
                    return new ResponseEntity<>(new RESTResponse.Success()
                            .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .setMessage("Some thing wrong").build(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
                exchangeRequest.setId(Calendar.getInstance().getTimeInMillis());
                Long gaid = exchangeRequest.getGift().getAccount().getId();
                requestService.sendSimpleMessage(accountInfo.getEmail(),
                        a.getUsername(),
                        exchangeRequest.getId(), exchangeRequest.getMessage(),
                        exchangeRequest.getGift().getThumbnail(),
                        gaid.toString());

                exchangeRequest.setOwnerId(gaid);
                exchangeRequest.setStatus(ExchangeRequest.Status.PENDING);
                requestService.store(exchangeRequest);
                return new ResponseEntity<>(new RESTResponse.Success()
                        .addData(exchangeRequest)
                        .setStatus(HttpStatus.CREATED.value())
                        .setMessage(" ").build(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .setMessage("Bạn không thể nhận món đồ của bạn").build(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setMessage("unauthorized").build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/_api/exchanges/gift/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getRequestOfGift(
            HttpServletRequest request,
            @PathVariable long id,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        Account account = (Account) request.getAttribute("account");
        if (account == null) {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.FORBIDDEN.value())
                    .setMessage("FORBIDDEN !").build(), HttpStatus.FORBIDDEN);
        }
        Page<ExchangeRequest> exchangeRequests = requestService.getRequestOfGift(id, account.getId(), page, limit);
        if (exchangeRequests != null) {
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setPagination(new RESTPagination(page, limit, exchangeRequests.getTotalPages(), exchangeRequests.getTotalElements()))
                    .addData(exchangeRequests.stream().map(x -> new RequestDTO(x)).collect(Collectors.toList()))
                    .setStatus(HttpStatus.OK.value())
                    .setMessage(" ").build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage(" ").build(), HttpStatus.NOT_FOUND);
        }

    }


    @RequestMapping(value = "/_api/exchanges/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> updateStatusRequest(
            HttpServletRequest request,
            @PathVariable long id,
            @RequestParam(value = "status", required = false) boolean status) {
        Account account = (Account) request.getAttribute("account");
        if (account == null) {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.FORBIDDEN.value())
                    .setMessage("FORBIDDEN !").build(), HttpStatus.FORBIDDEN);
        }
        ExchangeRequest exchangeRequest = requestService.updateStatusRequest(id, status, account);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .addData(new RequestDTO(exchangeRequest))
                .setMessage(" ").build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/exchanges/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> updateStatusRequestInMail(
            @PathVariable long id,
            @RequestParam(value = "status", required = false) boolean status,
            @RequestParam(value = "ref", required = false) String ref) {
        if (ref == null || ref == "") {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.FORBIDDEN.value())
                    .setMessage("FORBIDDEN !").build(), HttpStatus.FORBIDDEN);
        } else {
            Account account = accountService.findByAccountId(Long.parseLong(ref));
            if (account == null) {
                return new ResponseEntity<>(new RESTResponse.SimpleError()
                        .setCode(HttpStatus.FORBIDDEN.value())
                        .setMessage("FORBIDDEN !").build(), HttpStatus.FORBIDDEN);
            }
            ExchangeRequest exchangeRequest = requestService.updateStatusRequest(id, status, account);
            if (exchangeRequest != null) {
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .addData(new RequestDTO(exchangeRequest))
                        .setMessage(" ").build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new RESTResponse.SimpleError()
                        .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .setMessage("Lỗi hệ thống").build(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

    }


    @RequestMapping(value = "/_api/exchanges/receiver", method = RequestMethod.GET)
    public ResponseEntity<Object> getRequestOfReceiver(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        Account account = (Account) request.getAttribute("account");
        if (account == null) {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("Account not found in getRequestOfReceiver").build(), HttpStatus.OK);
        }
        Page<ExchangeRequest> exchangeRequests = requestService.getRequestOfReceiver(account.getId(), page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setPagination(new RESTPagination(page, limit, exchangeRequests.getTotalPages(), exchangeRequests.getTotalElements()))
                .addData(exchangeRequests.stream().map(x -> new RequestDTO(x)).collect(Collectors.toList()))
                .setStatus(HttpStatus.OK.value())
                .setMessage(" ").build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/_api/exchanges/owner", method = RequestMethod.GET)
    public ResponseEntity<Object> getRequestOfOwner(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        System.out.println("is receiver");
        Account account = (Account) request.getAttribute("account");
        System.out.println("is owner");
        if (account == null) {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("Account not found in getRequestOfReceiver").build(), HttpStatus.OK);
        }
        Page<ExchangeRequest> exchangeRequests = requestService.getRequestOfOwner(account.getId(), page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setPagination(new RESTPagination(page, limit, exchangeRequests.getTotalPages(), exchangeRequests.getTotalElements()))
                .addData(exchangeRequests.stream().map(x -> new RequestDTO(x)).collect(Collectors.toList()))
                .setStatus(HttpStatus.OK.value())
                .setMessage(" ").build(), HttpStatus.OK);
    }
}
