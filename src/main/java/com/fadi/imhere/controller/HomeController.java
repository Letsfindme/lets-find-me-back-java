package com.fadi.imhere.controller;

import com.fadi.imhere.dtos.PostDto;
import com.fadi.imhere.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @Autowired
    private PostService postService;


    @GetMapping(value = "/")
    protected String index(){
        return "Hello world";
    }

    @GetMapping(value = "/private")
    protected String privateArea(){
        return "Private area";
    }

    @GetMapping(value = "/findPaginated")
    protected Page<PostDto> findPaginated(@RequestParam("p") Integer page ,
                                          @RequestParam(value = "s", required = false)Integer size){
        Integer checkedSize = (size == null ? 4 : size);
        return  postService.findPaginated(checkedSize, page);
    }

}
