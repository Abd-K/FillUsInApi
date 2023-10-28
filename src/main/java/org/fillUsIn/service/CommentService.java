package org.fillUsIn.service;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreateCommentDTO;
import org.fillUsIn.entity.Comment;
import org.fillUsIn.entity.Post;
import org.fillUsIn.entity.User;
import org.fillUsIn.repository.CommentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
public class CommentService {

  private final CommentRepository commentRepository;
  private final UserService userService;
  private final PostService postService;

  public CommentService(CommentRepository commentRepository,
                        UserService userService, PostService postService) {
    this.commentRepository = commentRepository;
    this.userService = userService;
    this.postService = postService;
  }

  public Comment getCommentById(String commentId) {
    return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
  }

  public Comment createComment(CreateCommentDTO createCommentDTO) {
    final Post parentPost;
    final Comment parentComment;
    Comment comment = new Comment();
    if(createCommentDTO.getParentPostId() != null && !createCommentDTO.getParentPostId().equals("")) {
      parentPost = postService.getPostById(createCommentDTO.getParentPostId());
      comment.setPost(parentPost);
    } else if(createCommentDTO.getParentCommentId() != null) {
      parentComment = getCommentById(createCommentDTO.getParentCommentId());
      comment.setParentComment(parentComment);
    } else {
      throw new IllegalArgumentException("Missing postId or Comment Id");
    }
    comment.setBody(createCommentDTO.getBody());
    comment.setUser(fetchUser());
    return commentRepository.save(comment);
  }

  public Comment likeComment(String commentId) {
    User currentUser = fetchUser();
    Comment comment = getCommentById(commentId);

    if (!comment.getUserLikes().contains(currentUser)) {
      comment.getUserLikes().add(currentUser);
      comment.getUserDislikes().remove(currentUser);
      comment.setVoteCount(calculateVoteCount(comment));
    }
    commentRepository.saveAndFlush(comment);
    return comment;
  }

  public Comment dislikePost(String commentId) {
    User currentUser = fetchUser();

    Comment comment = getCommentById(commentId);
    if (!comment.getUserDislikes().contains(currentUser)) {
      comment.getUserDislikes().add(currentUser);
      comment.getUserLikes().remove(currentUser);
      comment.setVoteCount(calculateVoteCount(comment));
    }

    commentRepository.saveAndFlush(comment);
    return comment;
  }

  private User fetchUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();
    return userService.getUser(currentUsername);
  }

  private int calculateVoteCount(Comment comment) {
    return comment.getUserLikes().size() - comment.getUserDislikes().size();
  }
}
