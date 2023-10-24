package org.fillUsIn.service;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreateTopicDto;
import org.fillUsIn.dto.TopSubcategoryPostDto;
import org.fillUsIn.dto.TopicWithTopPostsDto;
import org.fillUsIn.entity.Post;
import org.fillUsIn.entity.Subcategory;
import org.fillUsIn.entity.Topic;
import org.fillUsIn.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class TopicService {

  private final TopicRepository topicRepository;
  private final SubCategoryService subCategoryService;
  private final PostService postService;

  public TopicService(TopicRepository topicRepository, SubCategoryService subCategoryService, PostService postService) {
    this.topicRepository = topicRepository;
    this.subCategoryService = subCategoryService;
    this.postService = postService;
  }

  public Topic createTopic(CreateTopicDto createTopicDto) {
    Topic topic = new Topic();
    topic.setTitle(createTopicDto.getTitle());
    for (String subcategoryName : createTopicDto.getSubcategoryNamesTopPick()) {
      Subcategory subcategory = subCategoryService.getSubcategory(subcategoryName);
      subcategory.getTopics().add(topic);
      topic.getSubcategories().add(subcategory);
    }
    for (String postId : createTopicDto.getPickedPostIds()) {
      Post post = postService.getPostById(postId);
      post.getTopics().add(topic);
      topic.getPickedPosts().add(post);
    }

    topicRepository.save(topic);
    return topic;
  }

  public List<TopicWithTopPostsDto> getAllTopicsWithTopPosts() {
    return topicRepository.findAll().stream()
            .map(this::getTopicWithTopPosts)
            .collect(Collectors.toList());
  }

  public TopicWithTopPostsDto getTopicWithTopPosts(Topic topic) {
    List<TopSubcategoryPostDto> topSubcategoryPosts = topic.getSubcategories().stream()
            .filter(subcategory -> !subcategory.getPosts().isEmpty())
            .map(subcategory -> new TopSubcategoryPostDto(subcategory.getName(), subcategory.getPosts().get(0)))
            .toList();

    List<TopSubcategoryPostDto> pickedPosts = topic.getPickedPosts().stream()
            .map(post -> new TopSubcategoryPostDto(post.getSubcategory().getName(), post)) // No subcategory name for these
            .toList();

    List<TopSubcategoryPostDto> topPosts = Stream.concat(topSubcategoryPosts.stream(), pickedPosts.stream())
            .toList();
    return new TopicWithTopPostsDto(topic.getTitle(), topPosts);
  }

}
