package org.fillUsIn.controller;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreatePostDto;
import org.fillUsIn.entity.Post;
import org.fillUsIn.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PostMapping ("/subcategory/{subCategoryName}")
  @ResponseStatus(HttpStatus.CREATED)
  public Post createPost(@PathVariable String subCategoryName, @Validated @RequestBody CreatePostDto createPostDto) {
    return postService.createPost(subCategoryName, createPostDto);
  }

  @GetMapping("/category/{categoryName}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public List<Post> getPostsByCategory(@PathVariable String categoryName) {
    return postService.getPostsByCategory(categoryName);
  }
  @GetMapping("/subcategory/{subCategoryName}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public List<Post> getPostsBySubCategory(@PathVariable String subCategoryName) {
    return postService.getPostsBySubcategory(subCategoryName);
  }

  @GetMapping ("/{postId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Post getPost(@PathVariable String postId) {
    return postService.getPostById(postId);
  }

  @PostMapping ("/{postId}/like")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Post likePost(@PathVariable String postId) {
    return postService.likePost(postId);
  }

  @PostMapping ("/{postId}/dislike")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Post dislikePost(@PathVariable String postId) {
    return postService.dislikePost(postId);
  }
}
