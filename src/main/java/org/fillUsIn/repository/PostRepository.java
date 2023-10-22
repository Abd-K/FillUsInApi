package org.fillUsIn.repository;

import org.fillUsIn.entity.Post;
import org.fillUsIn.entity.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository  extends JpaRepository<Post, String> {
    Page<Post> findBySubcategoryOrderByVoteCountDesc(Subcategory subcategory, Pageable pageable);

}
