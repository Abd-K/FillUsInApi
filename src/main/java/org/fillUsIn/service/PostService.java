package org.fillUsIn.service;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreatePostDto;
import org.fillUsIn.entity.Category;
import org.fillUsIn.entity.Post;
import org.fillUsIn.entity.Subcategory;
import org.fillUsIn.entity.User;
import org.fillUsIn.repository.PostRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
@Slf4j
public class PostService {

  private final PostRepository postRepository;
  private final SubCategoryService subCategoryService;
  private final CategoryService categoryService;
  private final UserService userService;

  public PostService(PostRepository postRepository, SubCategoryService subCategoryService, CategoryService categoryService, UserService userService) {
    this.postRepository = postRepository;
    this.subCategoryService = subCategoryService;
    this.categoryService = categoryService;
    this.userService = userService;
  }

  public Post getPostById(String postId) {
    return postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
  }

  public Post createPost(String subCategoryName, CreatePostDto createPostDto) {
    final Subcategory subcategory = subCategoryService.getSubcategory(subCategoryName);
    Post post = new Post();
    post.setSubcategory(subcategory);
    post.setTitle(createPostDto.getTitle());
    post.setBody(createPostDto.getBody());
    String url = createPostDto.getUrl();
    post.setUrl(url);
    post.setThumbnailUrl(ExtractThumbnail(url));
    post.setUsername(createPostDto.getUsername());
    return postRepository.save(post);
  }

  private String ExtractThumbnail(String url) {
    Document doc;
    try {
      doc = Jsoup.connect(url).get();
      final Elements elements = doc.select("meta[property=og:image]");
      if(elements.hasAttr("content")) {
        return elements.attr("content");
      } else {
        return null;
      }

    } catch (IOException e) {
      return null;
    }
  }

  public Page<Post> getPostsBySubcategory(String subCategoryName, int page, int size) {
    Subcategory subcategory = subCategoryService.getSubcategory(subCategoryName);
    PageRequest pageRequest = PageRequest.of(page, size);
    return postRepository.findBySubcategoryOrderByVoteCountDesc(subcategory, pageRequest);
  }

  public Page<Post> getPostsByCategory(String categoryName, int page, int size) {
    final Category category = categoryService.getCategory(categoryName);
    PageRequest pageRequest = PageRequest.of(page, size);
    return postRepository.findByCategoryOrderByVoteCountDesc(category, pageRequest);
  }

  public Post likePost(String postId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName(); // This will give you the username

    User currentUser = userService.getUser(currentUsername);
    Post post = getPostById(postId);

    if (!post.getUserLikes().contains(currentUser)) {
      post.getUserLikes().add(currentUser);
      post.getUserDislikes().remove(currentUser);
      post.setVoteCount(calculateVoteCount(post));
    }
    postRepository.saveAndFlush(post);
    return post;
  }

  public Post dislikePost(String postId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName(); // This will give you the username
    User currentUser = userService.getUser(currentUsername);

    Post post = getPostById(postId);
    if (!post.getUserDislikes().contains(currentUser)) {
      post.getUserDislikes().add(currentUser);
      post.getUserLikes().remove(currentUser);
      post.setVoteCount(calculateVoteCount(post));
    }

    postRepository.saveAndFlush(post);
    return post;
  }

  private int calculateVoteCount(Post post) {
    return post.getUserLikes().size() - post.getUserDislikes().size();
  }
}
