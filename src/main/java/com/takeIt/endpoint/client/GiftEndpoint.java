package com.takeIt.endpoint.client;

import com.google.gson.Gson;
import com.takeIt.dto.CategoryDTO;
import com.takeIt.dto.GiftDTO;
import com.takeIt.entity.Category;
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

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/_api/products")
public class GiftEndpoint {
    @Autowired
    GiftService giftService;
    @Autowired
    AccountService accountService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AddressService addressService;

    //get all or search gift by name
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> search(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "city", required = false) String cityName,
            @RequestParam(value = "district", required = false) String districtName,
            @RequestParam(value = "cate", required = false) String cateName,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        Specification specification = Specification.where(null);
        if (cityName != null && cityName.length() > 0) {
            System.out.println("cityName: "+cityName);
            specification = specification
                    .and(new GiftSpecification(new SearchCriteria("city", "join", cityName)));
        }
        if (districtName != null && districtName.length() > 0) {
            specification = specification
                    .and(new GiftSpecification(new SearchCriteria("district", "join", districtName)));
        }
        if (cateName != null && cateName.length() > 0) {
            specification = specification
                    .and(new GiftSpecification(new SearchCriteria("cate", "join", cateName)));
        }
        if (keyword != null && keyword.length() > 0) {
            specification = specification
                    .and(new GiftSpecification(new SearchCriteria("name", ":", keyword)));
        }

        Page<Gift> giftPage = giftService.giftssWithPaginate(specification, page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setPagination(new RESTPagination(page, limit, giftPage.getTotalPages(), giftPage.getTotalElements()))
                .addData(giftPage.getContent().stream().map(x -> new GiftDTO(x)).collect(Collectors.toList()))
                .setMessage("")
                .build(), HttpStatus.OK);
    }



    // get gift by cateId
    @RequestMapping(method = RequestMethod.GET, value = "/cate")
    public ResponseEntity<Object> searchCate(
            @RequestParam(value = "id", required = false) long id,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        Page<Gift> giftPage = giftService.getGiftByCategoryId(id, 0, page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setPagination(new RESTPagination(page, limit, giftPage.getTotalPages(), giftPage.getTotalElements()))
                .addData(giftPage.getContent().stream().map(x -> new GiftDTO(x)).collect(Collectors.toList()))
                .setMessage("")
                .build(), HttpStatus.OK);
    }

    // create gift
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<Object> saveProduct(@RequestBody Gift gift) {
        System.out.println(new Gson().toJson(gift));
        giftService.store(gift);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Save success!")
                .setStatus(HttpStatus.CREATED.value())
                .addData(gift).build(), HttpStatus.CREATED);
    }

    // update gift
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
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

    // getDetail gift
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
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

    // delete gift
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
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

    // get gift by gender " _api/products/gender?id=1"
    @RequestMapping(method = RequestMethod.GET, value = "/categories")
    public ResponseEntity<Object> getCate(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit
    ) {
        Page<Category> categories = categoryService.categories(page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("")
                .setPagination(new RESTPagination(page, limit, categories.getTotalPages(), categories.getTotalElements()))
                .addData(categories.getContent().stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList()))
                .setStatus(HttpStatus.OK.value()).build(), HttpStatus.OK);
    }

    // get gift by gender " _api/products/gender?id=1"
    @RequestMapping(method = RequestMethod.GET, value = "/gender")
    public ResponseEntity<Object> getGiftByGender(
            @RequestParam(value = "id", required = false) int gender,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit
    ) {
        Page<Gift> gifts = giftService.getGiftByGender(gender, page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("")
                .setPagination(new RESTPagination(page, limit, gifts.getTotalPages(), gifts.getTotalElements()))
                .addData(gifts.getContent().stream().map(x -> new GiftDTO(x)).collect(Collectors.toList()))
                .setStatus(HttpStatus.OK.value()).build(), HttpStatus.OK);
    }
}
