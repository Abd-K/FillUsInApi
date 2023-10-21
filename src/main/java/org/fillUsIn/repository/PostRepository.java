package org.fillUsIn.repository;

import org.fillUsIn.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository  extends JpaRepository<Post, String> {}
