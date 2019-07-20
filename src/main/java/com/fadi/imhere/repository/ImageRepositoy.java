package com.fadi.imhere.repository;

import com.fadi.imhere.model.Image;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;


/**
 * @author Fadi NOUFAL
 */
public interface ImageRepositoy extends PagingAndSortingRepository<Image, UUID> {

    public Image findByName(String name);
}
