package com.takeIt.controller;

import com.google.gson.Gson;
import com.takeIt.dto.GiftDTO;
import com.takeIt.entity.Gift;
import com.takeIt.rest.RESTPagination;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.AddressService;
import com.takeIt.service.account.AccountService;
import com.takeIt.service.category.CategoryService;
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

@RestController
@RequestMapping(value = "/_api/products")
public class GiftController {
    @Autowired
    GiftService giftService;
    @Autowired
    AccountService accountService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AddressService addressService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "ct_id", required = false) String cityId,
            @RequestParam(value = "d_id", required = false) String districtId,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        Specification specification = Specification.where(null);
        if (keyword != null && keyword.length() > 0) {
            specification = specification
                    .and(new GiftSpecification(new SearchCriteria("name", ":", keyword)))
                    .or(new GiftSpecification(new SearchCriteria("street_name", ":", keyword)));
        }

        Page<Gift> giftPage = giftService.giftssWithPaginate(specification, page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setPagination(new RESTPagination(page, limit, giftPage.getTotalPages(), giftPage.getTotalElements()))
                .addData(giftPage.getContent().stream().map(x -> new GiftDTO(x)).collect(Collectors.toList()))
                .setMessage("")
                .addData(giftService.getAll()).build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<Object> saveProduct(@RequestBody Gift gift) {
        System.out.println(new Gson().toJson(gift));
        giftService.store(gift);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Save success!")
                .setStatus(HttpStatus.CREATED.value())
                .addData(gift).build(), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Object> gifts() {

        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Save success!")
                .setStatus(HttpStatus.CREATED.value())
                .addData(giftService.getAll()).build(), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable long id, @RequestBody Gift gift) {
        Gift p = giftService.getProduct(id);
        if (p == null)
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("Product not found"), HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("update success!")
                    .addData(giftService.update(id, gift)), HttpStatus.OK);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/detail/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable long id) {
        Gift gift = giftService.getProduct(id);
        if (gift == null)
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("Product not found")
                    .build(),
                    HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Success")
                    .addData(new GiftDTO(gift))
                    .build(),
                    HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        if (giftService.delete(id))
            return new ResponseEntity<>(new RESTResponse.Success()
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
}
