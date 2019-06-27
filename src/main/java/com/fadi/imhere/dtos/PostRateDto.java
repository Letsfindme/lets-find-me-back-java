package com.fadi.imhere.dtos;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostRateDto {

    private UUID id;

    private String postid;

    private String username;

    private int rate;
}
