package com.takeIt.endpoint.client;

import com.takeIt.dto.CityDTO;
import com.takeIt.dto.DistrictDTO;
import com.takeIt.entity.City;
import com.takeIt.entity.District;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/_api/address")
public class AddressEndpoint {
    @Autowired
    AddressService addressService;

    Logger logger = LoggerFactory.getLogger(AddressEndpoint.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getDistrictByCityId(@RequestParam(value = "ct-id", required = false) long id) {
        List<District> districts = addressService.getDistrictByCityId(id);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Get success")
                .setStatus(HttpStatus.OK.value())
                .addData(districts.stream().map(x -> new DistrictDTO(x)).collect(Collectors.toList()))
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cities")
    public ResponseEntity<Object> getCities() {
        try {
            System.out.println("hello world");
            List<City> cities = addressService.getCities();
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setMessage("Get success")
                    .setStatus(HttpStatus.OK.value())
                    .addData(cities.stream().map(x -> new CityDTO(x)).collect(Collectors.toList()))
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setMessage("Some thing errors")
                    .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
