package com.fadi.imhere.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;
@Setter
@Getter
@ToString
@Entity
@Table(name = "user_adresse")
public class Address {

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    private User user;

    private String street;

    private String city;

    private int postcode;

}
