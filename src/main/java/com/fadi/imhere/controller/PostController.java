package com.fadi.imhere.controller;

import com.fadi.imhere.dtos.PostDto;
import com.fadi.imhere.dtos.PostRateDto;
import com.fadi.imhere.model.PostRate;
import com.fadi.imhere.service.PostServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostServiceImp postService;

    /**
     * Get all posts by user
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<PostDto> findAll() {
        return postService.findAll();
    }

//    , @RequestParam("user-id") UUID userId
    @RequestMapping(value = "/{postID}", method = RequestMethod.GET)
    @ResponseBody
    public PostDto getPostById(@PathVariable("postID") String postId) {
        return postService.getPostById(postId);
    }

    /**
     * Create a post
     * @param postDto
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostDto createPost(@RequestBody PostDto postDto) {
        if(postDto.getUsername() == null) {
            throw new InvalidParameterException("No author");
        }
        return postService.createPost(postDto);
    }

    @RequestMapping(value = "/addRate", method = RequestMethod.POST)
    @ResponseBody
    public PostRate addPostRate(@RequestBody PostRateDto postRateDto){
        System.out.println(postRateDto);
        return postService.addPostRate(postRateDto);
    }

}
