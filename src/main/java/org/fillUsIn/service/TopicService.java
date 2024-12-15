package org.fillUsIn.service;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreateTopicDTO;
import org.fillUsIn.dto.TopSubcategoryPostDTO;
import org.fillUsIn.dto.TopicWithTopPostsDTO;
import org.fillUsIn.dto.mapper.PostMapper;
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

  public Topic createTopic(CreateTopicDTO createTopicDto) {
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

  public List<TopicWithTopPostsDTO> getAllTopicsWithTopPosts() {
    return topicRepository.findAll().stream()
            .map(this::getTopicWithTopPosts)
            .collect(Collectors.toList());
  }

  public TopicWithTopPostsDTO getTopicWithTopPosts(Topic topic) {
    List<TopSubcategoryPostDTO> topSubcategoryPosts = topic.getSubcategories().stream()
            .filter(subcategory -> !subcategory.getPosts().isEmpty())
            .map(subcategory -> new TopSubcategoryPostDTO( subcategory.getCategory().getName(), subcategory.getName(), PostMapper.INSTANCE.postToPostSummaryDTO(subcategory.getPosts().get(0))))
            .toList();

    List<TopSubcategoryPostDTO> pickedPosts = topic.getPickedPosts().stream()
            .map(post -> new TopSubcategoryPostDTO(post.getSubcategory().getCategory().getName(), post.getSubcategory().getName(), PostMapper.INSTANCE.postToPostSummaryDTO(post))) // No subcategory name for these
            .toList();

    List<TopSubcategoryPostDTO> topPosts = Stream.concat(topSubcategoryPosts.stream(), pickedPosts.stream())
            .toList();
    return new TopicWithTopPostsDTO(topic.getTitle(), topPosts);
  }

}
