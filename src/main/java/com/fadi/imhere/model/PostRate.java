package com.fadi.imhere.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "post_rate")
public class PostRate {

    @Id
    @Type(type="uuid-char")
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int rate;
}
