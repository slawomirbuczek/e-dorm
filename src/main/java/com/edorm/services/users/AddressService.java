package com.edorm.services.users;

import com.edorm.entities.users.Address;
import com.edorm.exceptions.users.AddressNotFoundException;
import com.edorm.repositories.users.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address getAddress(long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }

}
