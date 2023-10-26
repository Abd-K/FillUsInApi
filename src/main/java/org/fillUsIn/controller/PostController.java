package org.fillUsIn.controller;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreatePostDTO;
import org.fillUsIn.dto.PostDTO;
import org.fillUsIn.entity.Post;
import org.fillUsIn.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<Page<Post>> getAllPosts(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size) {
    Page<Post> posts = postService.getAllPosts(page, size);
    return new ResponseEntity<>(posts, HttpStatus.ACCEPTED);
  }

  @GetMapping("/category/{categoryName}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<Page<Post>> getPostsByCategory(@PathVariable String categoryName,
                                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                                       @RequestParam(name = "size", defaultValue = "10") int size) {
    Page<Post> posts = postService.getPostsByCategory(categoryName, page, size);
    return new ResponseEntity<>(posts, HttpStatus.ACCEPTED);
  }

  @GetMapping("/subcategory/{subCategoryName}")
  public ResponseEntity<Page<Post>> getPostsBySubCategory(@PathVariable String subCategoryName,
                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "10") int size) {

    Page<Post> posts = postService.getPostsBySubcategory(subCategoryName, page, size);
    return new ResponseEntity<>(posts, HttpStatus.ACCEPTED);
  }

  @PostMapping("/subcategory/{subCategoryName}")
  @ResponseStatus(HttpStatus.CREATED)
  public Post createPost(@PathVariable String subCategoryName, @Validated @RequestBody CreatePostDTO createPostDto) {
    return postService.createPost(subCategoryName, createPostDto);
  }

  @GetMapping("/{postId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public PostDTO getPost(@PathVariable String postId) {
    return postService.getPostDTOById(postId);
  }

  @PostMapping("/{postId}/like")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Post likePost(@PathVariable String postId, HttpServletRequest request) {
    return postService.likePost(postId);
  }

  @PostMapping("/{postId}/dislike")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Post dislikePost(@PathVariable String postId) {
    return postService.dislikePost(postId);
  }
}
