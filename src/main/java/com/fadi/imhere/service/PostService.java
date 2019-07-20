package com.fadi.imhere.service;

import com.fadi.imhere.dtos.PostDto;
import org.springframework.data.domain.Page;

/**
 * @author Fadi NOUFAL
 */
public interface PostService {

    Page<PostDto> findPaginated(Integer size, Integer page);
}
