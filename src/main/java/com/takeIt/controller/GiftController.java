package com.takeIt.controller;

import com.google.gson.Gson;
import com.takeIt.dto.GiftDTO;
import com.takeIt.entity.Account;
import com.takeIt.entity.Category;
import com.takeIt.entity.Gift;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.AddressService;
import com.takeIt.service.account.AccountService;
import com.takeIt.service.category.CategoryService;
import com.takeIt.service.product.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setMessage(" ")
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
