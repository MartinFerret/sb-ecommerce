package com.ecommerce.sb_ecom.service.address;

import com.ecommerce.sb_ecom.dto.AddressDTO;
import com.ecommerce.sb_ecom.model.User;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAddresses();
}
