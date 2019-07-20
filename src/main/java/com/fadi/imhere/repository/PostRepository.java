package com.fadi.imhere.repository;

import com.fadi.imhere.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

   /*
    extends JpaRepository<Post, String>

    List findAvgById(String id);

    List<Void> deleteById(String id);*/


    @Query("select p from Post p order by SIZE(p.postLikes) desc")
    Page<Post> findPaginated(Pageable pageable);

    @Query(value = "select avg (rate) from world.post_rate\n" +
            "group by post_id having post_id = :postId",
            nativeQuery=true)
    Double findAvgById(@Param("postId") String postId);

}
