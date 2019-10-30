package com.takeIt.controller;

import com.google.gson.Gson;
import com.takeIt.dto.GiftDTO;
import com.takeIt.dto.RequestDTO;
import com.takeIt.entity.ExchangeRequest;
import com.takeIt.repository.ExchangeRequestRepository;
import com.takeIt.rest.RESTPagination;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.exchangeRequest.ExchangeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/_api/exchanges")
@CrossOrigin
public class ExchangeRequestController {
    @Autowired
    ExchangeRequestService requestService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> saveRequest(@RequestBody ExchangeRequest exchangeRequest) {
        if (exchangeRequest.getAccount().getId() != exchangeRequest.getGift().getAccount().getId()) {
            System.out.println("hello world");
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
}
