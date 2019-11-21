package com.takeIt.endpoint.admin;

import com.takeIt.dto.GiftDTO;
import com.takeIt.entity.Gift;
import com.takeIt.rest.RESTPagination;
import com.takeIt.rest.RESTResponse;
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
@RequestMapping(value = "/_api/admin/products")
public class GiftAdminEndpoint {
    @Autowired
    GiftService giftService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> search(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "city", required = false) String cityName,
            @RequestParam(value = "district", required = false) String districtName,
            @RequestParam(value = "cate", required = false) String cateName,
            @RequestParam(defaultValue = "0", required = false) int gender,
            @RequestParam(defaultValue = "0", required = false) int age,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit) {
        Specification specification = Specification.where(null);

        if (age > 0) {
            System.out.println("ageRange :" + age);
            specification = specification
                    .and(new GiftSpecification(new SearchCriteria("ageRange", ":", age)));
        }
        if (gender > 0) {
            System.out.println("Gender :" + gender);
            specification = specification
                    .and(new GiftSpecification(new SearchCriteria("gender", ":", gender)));
        }
        if (cityName != null && cityName.length() > 0) {
            System.out.println("cityName: " + cityName);
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

        Page<Gift> giftPage = giftService.giftsWithPaginatePublish(specification, page, limit);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setPagination(new RESTPagination(page, limit, giftPage.getTotalPages(), giftPage.getTotalElements()))
                .addData(giftPage.getContent().stream().map(x -> new GiftDTO(x)).collect(Collectors.toList()))
                .setMessage("")
                .build(), HttpStatus.OK);
    }

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

    //Confirm the gift by status ""
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

}
