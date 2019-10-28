package com.takeIt.controller;

import com.google.gson.Gson;
import com.takeIt.dto.CityDTO;
import com.takeIt.dto.DistrictDTO;
import com.takeIt.dto.GiftDTO;
import com.takeIt.entity.City;
import com.takeIt.entity.District;
import com.takeIt.rest.RESTResponse;
import com.takeIt.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/_api/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getDistrictByCityId(@RequestParam(value = "ct-id", required = false) long id) {
        List<District> districts = addressService.getDistrictByCityId(id);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Get success")
                .setStatus(HttpStatus.OK.value())
                .addData(districts.stream().map(x -> new DistrictDTO(x)).collect(Collectors.toList()))
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/cities")
    public ResponseEntity<Object> getCities() {
        List<City> cities = addressService.getCities();
        return new ResponseEntity<>(new RESTResponse.Success()
                .setMessage("Get success")
                .setStatus(HttpStatus.OK.value())
                .addData(cities.stream().map(x -> new CityDTO(x)).collect(Collectors.toList()))
                .build(), HttpStatus.OK);
    }
}
