package com.tomatorun.dto.builder;

import com.tomatorun.dto.Address;

/**
 * @author:Gavin
 * @date 2015/2/25
 */
public class AddressBuilder implements DtoBuilder<Address> {

    private Address address = new Address();

    public AddressBuilder id(Long id){
        address.setId(id);
        return this;
    }

    public AddressBuilder address(String addressValue){
        address.setAddress(addressValue);
        return this;
    }

    public AddressBuilder consignee(String consignee){
        address.setConsignee(consignee);
        return this;
    }

    public AddressBuilder userId(Long userId){
        address.setUserId(userId);
        return this;
    }

    public AddressBuilder isDefault(Boolean isDefault){
        address.setIsDefault(isDefault);
        return this;
    }

    public AddressBuilder phoneNumber(String phoneNumber){
        address.setPhoneNumber(phoneNumber);
        return this;
    }

    @Override
    public Address build() {
        return address;
    }
}
