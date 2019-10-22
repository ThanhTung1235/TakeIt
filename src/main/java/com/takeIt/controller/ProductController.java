package com.takeIt.controller;

import com.takeIt.dto.ProductDTO;
import com.takeIt.entity.Account;
import com.takeIt.entity.Address;
import com.takeIt.entity.Category;
import com.takeIt.entity.Product;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.account.AccountService;
import com.takeIt.service.address.AddressService;
import com.takeIt.service.category.CategoryService;
import com.takeIt.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/_api/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    AddressService addressService;
    @Autowired
    AccountService accountService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAll() {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<Object> saveProduct(@RequestBody ProductDTO productDTO) {
        Address address = addressService.getAddress(1);
        Account account = accountService.getAccount(1);
        Category category = categoryService.getCategory(productDTO.getCategory_id());
        productService.store(new Product.ProductBuilder()
                .setAccount(account)
                .setAddress(address)
                .setAge_range(productDTO.getAge_range())
                .setCategory(category)
                .setAccount(account)
                .setName(productDTO.getName())
                .setDescription(productDTO.getDescription())
                .setGender(Product.Gender.findByValue(productDTO.getGender()))
                .build());
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Save success!")
                .setStatus(HttpStatus.CREATED.value())
                .addData(productDTO).build(), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable long id, @RequestBody Product product) {
        Product p = productService.getProduct(id);
        if (p == null)
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("Product not found"), HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("update success!")
                    .addData(productService.update(id, product)), HttpStatus.OK);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/detail/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable long id) {
        Product product = productService.getProduct(id);
        if (product == null)
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("Product not found")
                    .build(),
                    HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Success")
                    .addData(product)
                    .build(),
                    HttpStatus.OK);
    }
}
