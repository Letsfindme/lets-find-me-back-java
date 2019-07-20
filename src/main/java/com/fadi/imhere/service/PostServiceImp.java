package com.fadi.imhere.service;

import com.fadi.imhere.Utils.DtoUtils;
import com.fadi.imhere.dtos.PostDto;
import com.fadi.imhere.dtos.PostRateDto;
import com.fadi.imhere.model.Post;

import com.fadi.imhere.model.PostRate;
import com.fadi.imhere.model.User;
import com.fadi.imhere.repository.PostRateRepository;
import com.fadi.imhere.repository.PostRepository;
import com.fadi.imhere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Fadi NOUFAL
 */
@Service
public class PostServiceImp implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostRateRepository postRateRepository;

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
    public PostDto getPostById(String postId) {
        Post post = postRepository.findById(UUID.fromString(postId)).get();
        PostDto postDto = null;

        if (post != null) {
            postDto = (PostDto) DtoUtils.convertPostToDto(post);
            addStars(postDto);
        }
        else {
            System.out.println("No post found in data base with the id '{}'"+ postId);
        }
        return postDto;
    }


    @Override
    public Page<PostDto> findPaginated(Integer size, Integer page) {
        PostDto postToReturn = null;
        Pageable pageable = PageRequest.of(page, size);
        List<PostDto> postDtoList = new ArrayList<>();

        System.out.println("==========================================================");


        Stream<PostDto> postDtoStream = postRepository.findPaginated(pageable)
                .stream()
                .map(post -> DtoUtils.convertPostToDto(post));
        postDtoStream.forEach(
                postDto -> {
                    addStars(postDto);
                    postDtoList.add(postDto);
                }
        );
        return new PageImpl(postDtoList);
    }


    public PostDto addStars(PostDto postDto){
        Double average = postRepository.findAvgById(postDto.getId().toString());
        if (average != null) {
            postDto.setStarCount((int) Math.round(average));
        } else {
            postDto.setStarCount(0);
        }
        return postDto;
    }

    public PostRate addPostRate(PostRateDto postRateDto) {
        PostRate postRateToSave = new PostRate();
        Post post = postRepository.findById(UUID.fromString(postRateDto.getPostid())).get();
        postRateToSave.setPost(post);
        postRateToSave.setUser(userRepository.findByUsername(postRateDto.getUsername()).get());
        postRateToSave.setRate(postRateDto.getRate());
        return postRateRepository.save(postRateToSave);
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
