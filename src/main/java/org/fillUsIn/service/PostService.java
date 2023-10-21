package org.fillUsIn.service;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.entity.Post;
import org.fillUsIn.repository.PostRepository;
import org.fillUsIn.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostService {

  private final PostRepository postRepository;
  private final SubCategoryRepository subCategoryRepository;

  public PostService(PostRepository postRepository, SubCategoryRepository subCategoryRepository) {
    this.postRepository = postRepository;
    this.subCategoryRepository = subCategoryRepository;
  }

//  public Post createPost(String communityId, CreatePostDto createPostDto) {
//    Community community = communityRepository.findById(communityId).get();
//
//    Post post = new Post();
//    post.setCommunity(community);
//    post.setTitle(createPostDto.getTitle());
//
//    if (!createPostDto.getBody().trim().isEmpty()) {
//      post.setBody(createPostDto.getBody());
//    }
//
//    return postRepository.save(post);
//  }
}
