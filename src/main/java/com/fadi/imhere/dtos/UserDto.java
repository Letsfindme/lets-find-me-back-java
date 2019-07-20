package com.fadi.imhere.dtos;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@ToString

public class UserDto {

    private UUID id;
    private String email;
    private String firstname;
    private String lastname;
    private String country;
    private String username;
    private Date firstConnection;
    private Date lastConnection;
    private RoleDto role;
    private int age;
    private List<AddressDto> address;
}
