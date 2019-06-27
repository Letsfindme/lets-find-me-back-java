package com.fadi.imhere.repository;

import com.fadi.imhere.model.PostRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public
interface PostRateRepository extends JpaRepository<PostRate, UUID> {
}
