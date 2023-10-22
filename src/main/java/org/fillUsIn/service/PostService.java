package org.fillUsIn.service;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreatePostDto;
import org.fillUsIn.entity.Category;
import org.fillUsIn.entity.Post;
import org.fillUsIn.entity.Subcategory;
import org.fillUsIn.repository.CategoryRepository;
import org.fillUsIn.repository.PostRepository;
import org.fillUsIn.repository.SubCategoryRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class PostService {

  private final PostRepository postRepository;
  private final SubCategoryRepository subCategoryRepository;
  private final CategoryRepository categoryRepository;

  public PostService(PostRepository postRepository, SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
    this.postRepository = postRepository;
    this.subCategoryRepository = subCategoryRepository;
    this.categoryRepository = categoryRepository;
  }

  public Post createPost(String subCategoryName, CreatePostDto createPostDto) {
    final Subcategory subcategory = subCategoryRepository.findById(subCategoryName)
            .orElseThrow(() -> new EntityNotFoundException("Subcategory not found with id: " + subCategoryName));
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


  private String ExtractThumbnail(String url)
  {
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

  public List<Post> getPostsBySubcategory(String subCategoryName) {
    final Subcategory subcategory = subCategoryRepository.findByNameIgnoreCase(subCategoryName)
            .orElseThrow(() -> new EntityNotFoundException("Subcategory not found with id: " + subCategoryName));

    return subcategory.getPosts();
  }

  public List<Post> getPostsByParentCategory(String parentCategoryName) {
    final Category parentCategory = categoryRepository.findByNameIgnoreCase(parentCategoryName)
            .orElseThrow(() -> new EntityNotFoundException("Parent category not found with name: " + parentCategoryName));

    List<Post> allPosts = new ArrayList<>();

    for (Subcategory subcategory : parentCategory.getSubcategories()) {
      allPosts.addAll(subcategory.getPosts());
    }

    allPosts.sort(Comparator.comparing(Post::getVoteCount).reversed());
    return allPosts;
  }

  public Post getPostById(String postId) {
    return postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
  }

  public Post likePost(String postId) {
    Post post = postRepository.findById(postId) .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

    post.setVoteCount(post.getVoteCount()+1);
    postRepository.saveAndFlush(post);
    return post;
  }

  public Post dislikePost(String postId) {
    Post post = postRepository.findById(postId) .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
    post.setVoteCount(post.getVoteCount() - 1);
    postRepository.saveAndFlush(post);
    return post;
  }
}
