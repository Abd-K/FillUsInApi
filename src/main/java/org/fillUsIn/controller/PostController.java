package org.fillUsIn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("posts")
@Slf4j
public class PostController {
//
//  private final PostService postService;
//
//  public PostController(PostService postService) {
//    this.postService = postService;
//  }
//
//  @PostMapping
//  @ResponseStatus(HttpStatus.CREATED)
//  public Post createPost(@PathVariable String communityId, @RequestBody CreatePostDto createPostDto) {
//    return postService.createPost(communityId, createPostDto);
//  }
}
