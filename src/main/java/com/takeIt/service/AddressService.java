package com.takeIt.service;

import com.takeIt.entity.City;
import com.takeIt.entity.District;
import com.takeIt.repository.CityRepository;
import com.takeIt.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    CityRepository cityRepository;
    @Autowired
    DistrictRepository districtRepository;

    public City getCity(long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public District getDistrict(long id) {
        return districtRepository.findById(id).orElse(null);
    }
}
