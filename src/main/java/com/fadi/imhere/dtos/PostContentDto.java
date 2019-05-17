package com.fadi.imhere.dtos;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostContentDto {

    private String  id;

    private String title;

    private String text;

    private String videoUrl;

    private PostDto postDto;

}
