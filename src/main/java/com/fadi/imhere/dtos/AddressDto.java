package com.fadi.imhere.dtos;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Setter
@Getter
@ToString
public class AddressDto {

    private UUID id;

    private UserDto user;

    private String street;

    private String city;

    private int postcode;
}
