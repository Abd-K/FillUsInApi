package org.fillUsIn.repository;

import org.fillUsIn.entity.Category;
import org.fillUsIn.entity.Post;
import org.fillUsIn.entity.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PostRepository  extends JpaRepository<Post, String> {
    Page<Post> findBySubcategoryOrderByVoteCountDesc(Subcategory subcategory, Pageable pageable);
    Page<Post> findAllByOrderByVoteCountDesc(Pageable pageable);

    @Query("SELECT post FROM Post post JOIN post.subcategory subcategory WHERE subcategory.category = :category ORDER BY post.voteCount DESC")
    Page<Post> findByCategoryOrderByVoteCountDesc(@Param("category")Category category, Pageable pageable);
}
