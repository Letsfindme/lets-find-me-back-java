package com.fadi.imhere.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user_details")
public class User implements Serializable {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private boolean active;

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(nullable = false)
    private String username;

    @Column(name = "first_connection")
    private Date firstConnection;

    @Column(name = "last_connection")
    private Date lastConnection;

    private String country;

    @Column(name = "avatar")
    private boolean avatar;

    @Column (nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private LocalDateTime createdDate;

    private LocalDateTime birthday;

    @Column(name = "age")
    private int age;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> address;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PostRate> postRates;

    public User(String firstname, String username, String email, String password) {
        this.firstname = firstname;
        this.username = username;
        this.email = email;
        this.password = password;
    }


}
