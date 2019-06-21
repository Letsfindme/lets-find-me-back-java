package com.fadi.imhere.service;

import com.fadi.imhere.Utils.DtoUtils;
import com.fadi.imhere.dtos.PostDto;
import com.fadi.imhere.model.Post;

import com.fadi.imhere.model.User;
import com.fadi.imhere.repository.PostRepository;
import com.fadi.imhere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    private PostRepository postRepository;

    private DtoUtils dtoUtils;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Transactional
    public PostDto createPost(PostDto postDto) {
        PostDto postToReturn = null;
        Post post = DtoUtils.convertPostToEntity(postDto);
        UUID userId = userRepository.findByUsername((postDto.getUsername())).get().getId();
        Optional<User> user = userRepository.findById(userId);
        if (user != null) {
            post.setUser(user.get());
            Post savedPost = postRepository.save(post);
            postToReturn = DtoUtils.convertPostToDto(savedPost);
        }
        return postToReturn;
    }

    @Transactional(readOnly = true)
    public List<PostDto> findAll() {
        return DtoUtils.makeList(postRepository.findAll())
                .stream()
                .map(p -> DtoUtils.convertPostToDto(p))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDto getPostById(UUID postId, UUID userId) {
        //Post post = postRepository.findById(postId);
        PostDto postDto = null;

        /*
        if (post != null) {
            postDto = (PostDto) DtoUtils.convertPostToDto(post, userId);
        }
        else {
            System.out.println("No post found in data base with the id '{}'"+ postId);
        }*/
        return postDto;
    }


    @Override
    public Page<PostDto> findPaginated(Integer size, Integer page) {
        PostDto postToReturn = null;
        Pageable pageable = PageRequest.of(page, size);
        List<PostDto> postDtoList = new ArrayList<>();

        System.out.println("==========================================================");
        System.out.println(Math.round(postRepository.findById("191e85d7-e867-4060-916c-ded57ed12169")));


        Stream<PostDto> postDtoStream = postRepository.findPaginated(pageable)
                .stream()
                .map(post -> DtoUtils.convertPostToDto(post));
        postDtoStream.forEach(
                postDto -> {
                    Double average = postRepository.findById(postDto.getId().toString());
                    if (average != null) {
                        postDto.setStarCount((int) Math.round(average));
                    } else {
                        postDto.setStarCount(0);
                    }
                    postDtoList.add(postDto);
                }
        );
        return new PageImpl(postDtoList);
    }


//.filter(post-> !post.getPostRates().isEmpty())
//    final Page<PostDto> postsPage = new PageImpl(
//            postRepository.findPaginated(pageable)
//                    .stream()
//                    .map(post -> DtoUtils.convertPostToDto(post))
//                    .forEach(postDto -> postDto.setStarCount((Math.round(postRepository.rateStars(postDto.getId().toString())))))
//
//    );
//    .setStarCount((Math.round(postRepository.rateStars(post.getId().toString()))))
//    postRepository.findPaginated(pageable)
//            .stream()
//                        .map(post -> DtoUtils.convertPostToDto(post))
//            .forEach(postDto -> postDto.setStarCount((Math.round(postRepository.rateStars(postDto.getId().toString())))))
//            .collect(Collectors.toList())
}
