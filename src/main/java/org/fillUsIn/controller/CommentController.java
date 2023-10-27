package org.fillUsIn.controller;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreateCommentDTO;
import org.fillUsIn.entity.Comment;
import org.fillUsIn.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/comments")
@Slf4j
public class CommentController {
  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Comment> createComment(@Validated @RequestBody CreateCommentDTO createCommentDTO) {
    Comment comment = commentService.createComment(createCommentDTO);
    return new ResponseEntity<>(comment, HttpStatus.CREATED);
  }

  @PostMapping("/{commentId}/like")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Comment likeComment(@PathVariable String commentId) {
    return commentService.likeComment(commentId);
  }

  @PostMapping("/{commentId}/dislike")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Comment dislikeComment(@PathVariable String commentId) {
    return commentService.dislikePost(commentId);
  }
}
