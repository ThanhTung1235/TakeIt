package com.takeIt.endpoint.admin;

import com.takeIt.dto.GiftDTO;
import com.takeIt.entity.Account;
import com.takeIt.entity.Gift;
import com.takeIt.rest.RESTPagination;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.account.AccountService;
import com.takeIt.service.gift.GiftService;
import com.takeIt.specification.GiftSpecification;
import com.takeIt.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/_api/admin/product")
public class giftAdminEndpoint {
    @Autowired
    GiftService giftService;


    //Sửa Gift
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable long id, @RequestBody Gift gift) {
        Gift p = giftService.getProduct(id);
        if (p == null)
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("Product not found"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Update success")
                    .addData(giftService.update(id, gift)), HttpStatus.OK);
    }

    //Xóa Gift
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id){
        if (giftService.delete(id))
            return new ResponseEntity<>( new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Deleted!")
                    .build(),
                    HttpStatus.OK);
        else
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("Delete fail!")
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Confirm the gift by status
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Object> confirmGift(
            @PathVariable long id,
            @RequestParam(value = "status", required = false) boolean status) {
        try {
            giftService.updateStatusGift(id, status);
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage(" ").build(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setMessage("server error").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


//    //Search
//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<Object> search(
//            @RequestParam(value = "keyword", required = false) String keyword,
//            @RequestParam(defaultValue = "1", required = false) int page,
//            @RequestParam(defaultValue = "10", required = false) int limit) {
//        Specification specification = Specification.where(null);
//        if (keyword != null && keyword.length() > 0) {
//            specification = specification
//                    .and(new GiftSpecification(new SearchCriteria("name", ":", keyword)))
//                    .or(new GiftSpecification(new SearchCriteria("description", ":", keyword)))
//                    .or(new GiftSpecification(new SearchCriteria("street_name", ":", keyword)));
//        }
//
//        Page<Gift> giftPage = giftService.giftssWithPaginate(specification, page, limit);
//        return new ResponseEntity<>(new RESTResponse.Success()
//                .setStatus(HttpStatus.OK.value())
//                .setPagination(new RESTPagination(page, limit, giftPage.getTotalPages(), giftPage.getTotalElements()))
//                .addData(giftPage.getContent().stream().map(x -> new GiftDTO(x)).collect(Collectors.toList()))
//                .setMessage("")
//                .build(), HttpStatus.OK);
//    }
//
//    //Get product
//    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
//    public ResponseEntity<Object> getProduct(@PathVariable long id) {
//        Gift gift = giftService.getProduct(id);
//        if (gift == null)
//            return new ResponseEntity<>(new RESTResponse.SimpleError()
//                    .setCode(HttpStatus.NOT_FOUND.value())
//                    .setMessage("Product not found")
//                    .build(),
//                    HttpStatus.NOT_FOUND);
//        else
//            return new ResponseEntity<>(new RESTResponse.Success()
//                    .setStatus(HttpStatus.OK.value())
//                    .setMessage("Success")
//                    .addData(new GiftDTO(gift))
//                    .build(),
//                    HttpStatus.OK);
//    }


}
