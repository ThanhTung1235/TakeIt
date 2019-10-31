package com.takeIt.endpoint;

import com.takeIt.dto.AccountInfoDTO;
import com.takeIt.entity.AccountInfo;
import com.takeIt.rest.RESTPagination;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.account.AccountInfoService;
import com.takeIt.specification.GiftSpecification;
import com.takeIt.specification.SearchCriteria;
import com.takeIt.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/_api/v1/accountInfo")
public class AcountInfoEndPoint {
    @Autowired
    AccountInfoService accountInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        Specification specification = Specification.where(null);
        if (keyword != null && keyword.length() > 0) {
            specification = specification
                    .and(new GiftSpecification(new SearchCriteria("firstName", ":", keyword)))
                    .and(new GiftSpecification(new SearchCriteria("lastName", ":", keyword)))
                    .or(new GiftSpecification(new SearchCriteria("email", ":", keyword)))
                    .or(new GiftSpecification(new SearchCriteria("rollNumber", ":", keyword)));
        }
        Page<AccountInfo> heroPage = accountInfoService.getAccountInfoWithPaginate(specification, page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Action success!")
                .addData(heroPage.getContent().stream().map(x -> new AccountInfoDTO(x)).collect(Collectors.toList()))
                .setPagination(new RESTPagination(page, limit, heroPage.getTotalPages(), heroPage.getTotalElements()))
                .build(),
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Object> getDetail(@PathVariable String id) {
        AccountInfo accountInfo = accountInfoService.findByAccountId(Long.parseLong(id));
        if (accountInfo == null) {
            return new ResponseEntity<>(new JsonResponse()
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setMessage("Not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new JsonResponse()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Success")
                .setData(new AccountInfoDTO(accountInfoService.findByAccountId(Long.parseLong(id)))),
                HttpStatus.OK);
    }
}
