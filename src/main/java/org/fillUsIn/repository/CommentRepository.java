package org.fillUsIn.repository;

import org.fillUsIn.entity.Comment;
import org.fillUsIn.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {


}

