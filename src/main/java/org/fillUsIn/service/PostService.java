package org.fillUsIn.service;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreatePostDto;
import org.fillUsIn.entity.Post;
import org.fillUsIn.entity.Subcategory;
import org.fillUsIn.repository.PostRepository;
import org.fillUsIn.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
public class PostService {

  private final PostRepository postRepository;
  private final SubCategoryRepository subCategoryRepository;

  public PostService(PostRepository postRepository, SubCategoryRepository subCategoryRepository) {
    this.postRepository = postRepository;
    this.subCategoryRepository = subCategoryRepository;
  }

  public Post createPost(String subCategoryName, CreatePostDto createPostDto) {
    final Subcategory subcategory = subCategoryRepository.findById(subCategoryName)
            .orElseThrow(() -> new EntityNotFoundException("Subcategory not found with id: " + subCategoryName));
    Post post = new Post();
    post.setSubcategory(subcategory);
    post.setTitle(createPostDto.getTitle());
    post.setBody(createPostDto.getBody());
    post.setUsername(createPostDto.getUsername());
    return postRepository.save(post);
  }

  public List<Post> getPostsBySubcategory(String subCategoryName) {
    final Subcategory subcategory = subCategoryRepository.findById(subCategoryName)
            .orElseThrow(() -> new EntityNotFoundException("Subcategory not found with id: " + subCategoryName));

    return subcategory.getPosts();
  }

  public Post getPostById(String postId) {
    return postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
  }
}
