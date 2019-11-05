package com.takeIt.endpoint.client;

import com.takeIt.dto.RequestDTO;
import com.takeIt.entity.AccountInfo;
import com.takeIt.entity.ExchangeRequest;
import com.takeIt.rest.RESTPagination;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.accountInfo.AccountInfoService;
import com.takeIt.service.exchangeRequest.ExchangeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Calendar;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/_api/exchanges")
@CrossOrigin
public class ExchangeRequestEndpoint {
    @Autowired
    ExchangeRequestService requestService;
    @Autowired
    AccountInfoService infoService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> saveRequest(@RequestBody ExchangeRequest exchangeRequest) throws MessagingException {
        if (exchangeRequest.getAccount().getId() != exchangeRequest.getGift().getAccount().getId()) {
            AccountInfo accountInfo = infoService.getAccountInfoByAccountId(exchangeRequest.getGift().getAccount().getId());
            if (accountInfo == null) {
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .setMessage("Some thing wrong").build(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            exchangeRequest.setId(Calendar.getInstance().getTimeInMillis());
            requestService.sendSimpleMessage(accountInfo.getEmail(), exchangeRequest.getAccount().getUsername(), exchangeRequest.getId(), "Just test email text", "https://media.shoptretho.com.vn/upload/image/product/20170721/xe-day-tre-em-gluck-b6-2017-2.jpg");
            exchangeRequest.setStatus(ExchangeRequest.Status.PENDING);
            requestService.store(exchangeRequest);
            return new ResponseEntity<>(new RESTResponse.Success()
                    .addData(exchangeRequest)
                    .setStatus(HttpStatus.CREATED.value())
                    .setMessage(" ").build(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("You can't get your gift").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/gift/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getRequestOfGift(
            @PathVariable long id,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        Page<ExchangeRequest> exchangeRequests = requestService.getRequestOfGift(id, page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setPagination(new RESTPagination(page, limit, exchangeRequests.getTotalPages(), exchangeRequests.getTotalElements()))
                .addData(exchangeRequests.stream().map(x -> new RequestDTO(x)).collect(Collectors.toList()))
                .setStatus(HttpStatus.CREATED.value())
                .setMessage(" ").build(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/receiver/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getRequestOfReceiver(
            @PathVariable long id,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        Page<ExchangeRequest> exchangeRequests = requestService.getRequestOfReceiver(id, page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setPagination(new RESTPagination(page, limit, exchangeRequests.getTotalPages(), exchangeRequests.getTotalElements()))
                .addData(exchangeRequests.stream().map(x -> new RequestDTO(x)).collect(Collectors.toList()))
                .setStatus(HttpStatus.OK.value())
                .setMessage(" ").build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> updateStatusRequest(
            @PathVariable long id,
            @RequestParam(value = "status", required = false) boolean status) {
        ExchangeRequest exchangeRequest = requestService.updateStatusRequest(id, status);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .addData(new RequestDTO(exchangeRequest))
                .setMessage(" ").build(), HttpStatus.OK);
    }
}
