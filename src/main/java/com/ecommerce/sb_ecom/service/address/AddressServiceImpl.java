package com.ecommerce.sb_ecom.service.address;

import com.ecommerce.sb_ecom.dto.AddressDTO;
import com.ecommerce.sb_ecom.model.Address;
import com.ecommerce.sb_ecom.model.User;
import com.ecommerce.sb_ecom.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;

    public AddressServiceImpl(ModelMapper modelMapper, AddressRepository addressRepository) {
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);
        List<Address> addressList = user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);

        return modelMapper.map(savedAddress, AddressDTO.class);
    }
}
