package org.fillUsIn.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fillUsIn.dto.CreatePostDTO;
import org.fillUsIn.dto.PostDTO;
import org.fillUsIn.dto.mapper.PostMapper;
import org.fillUsIn.entity.Category;
import org.fillUsIn.entity.Post;
import org.fillUsIn.entity.Subcategory;
import org.fillUsIn.entity.User;
import org.fillUsIn.repository.PostRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Service
@Slf4j
public class PostService {

  private final WebClient webClient;
  private final PostRepository postRepository;
  private final SubCategoryService subCategoryService;
  private final CategoryService categoryService;
  private final UserService userService;

  public PostService(PostRepository postRepository,
                     SubCategoryService subCategoryService,
                     CategoryService categoryService,
                     UserService userService) {
    this.webClient = WebClient.builder().build();
    this.postRepository = postRepository;
    this.subCategoryService = subCategoryService;
    this.categoryService = categoryService;
    this.userService = userService;
  }

  public Post getPostById(String postId) {
    return postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
  }

  public PostDTO getPostDTOById(String postId) {
    return convertToDto(getPostById(postId));
  }

  private PostDTO convertToDto(Post post) {
    return PostMapper.INSTANCE.postToPostDTO(post);
  }

  public Mono<Post> createPost(String subCategoryName, CreatePostDTO createPostDto) {
    return Mono.defer(() -> {
              final Subcategory subcategory = subCategoryService.getSubcategory(subCategoryName);
              Post post = new Post();
              post.setSubcategory(subcategory);
              post.setTitle(createPostDto.getTitle());
              post.setBody(createPostDto.getBody());
              post.setUrl(createPostDto.getUrl());
              post.setUser(fetchUser());
              return Mono.just(post);
            })
            .publishOn(Schedulers.boundedElastic())
            .map(postRepository::save)
            .doOnNext(savedPost -> {
              if (!StringUtils.isEmpty(savedPost.getUrl())) {
                fetchThumbnailFromUrl(savedPost.getUrl())
                        .doOnNext(thumbnail -> {
                          savedPost.setThumbnailUrl(thumbnail);
                          postRepository.save(savedPost);
                        })
                        .subscribe();
              }
            });
  }

  public Mono<String> fetchThumbnailFromUrl(String url) {
    return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToFlux(DataBuffer.class)  // Stream in the data
            .map(dataBuffer -> {
              byte[] bytes = new byte[dataBuffer.readableByteCount()];
              dataBuffer.read(bytes);
              DataBufferUtils.release(dataBuffer);
              return new String(bytes, StandardCharsets.UTF_8);
            })
            .window(2, 1)
            .concatMap(flux -> flux.reduce(String::concat))
            .takeUntil(s -> s.contains("img>") || s.contains("meta>"))
            .reduce(String::concat)
            .map(this::parseHtmlForThumbnail)
            .timeout(Duration.ofSeconds(10))
            .onErrorResume(e -> {
              log.warn("Error fetching thumbnail from {}: {}", url, e.getMessage());
              return Mono.empty();
            });
  }

  private String parseHtmlForThumbnail(String html) {
    Document document = Jsoup.parse(html);
    return document.select("meta[property=og:image]").attr("content");
  }

  public Page<Post> getAllPosts(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return postRepository.findAllByOrderByVoteCountDesc(pageRequest);
  }

  public Page<Post> getPostsByCategory(String categoryName, int page, int size) {
    final Category category = categoryService.getCategory(categoryName);
    PageRequest pageRequest = PageRequest.of(page, size);
    return postRepository.findByCategoryOrderByVoteCountDesc(category, pageRequest);
  }

  public Page<Post> getPostsBySubcategory(String subCategoryName, int page, int size) {
    Subcategory subcategory = subCategoryService.getSubcategory(subCategoryName);
    PageRequest pageRequest = PageRequest.of(page, size);
    return postRepository.findBySubcategoryOrderByVoteCountDesc(subcategory, pageRequest);
  }

  public Post likePost(String postId) {
    User currentUser = fetchUser();
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
    User currentUser = fetchUser();

    Post post = getPostById(postId);
    if (!post.getUserDislikes().contains(currentUser)) {
      post.getUserDislikes().add(currentUser);
      post.getUserLikes().remove(currentUser);
      post.setVoteCount(calculateVoteCount(post));
    }

    postRepository.saveAndFlush(post);
    return post;
  }

  private User fetchUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();
    User currentUser = userService.getUser(currentUsername);
    return currentUser;
  }

  private int calculateVoteCount(Post post) {
    return post.getUserLikes().size() - post.getUserDislikes().size();
  }
}
