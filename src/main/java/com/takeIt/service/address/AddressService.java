package com.takeIt.service.address;

import com.takeIt.entity.Address;

public interface AddressService {
    Address getAddress(long id);

    Address createAddress(Address address);
}
