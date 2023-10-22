package org.fillUsIn.service;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreateTopicDto;
import org.fillUsIn.entity.Post;
import org.fillUsIn.entity.Topic;
import org.fillUsIn.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TopicService {

  private final TopicRepository topicRepository;
  private final PostService postService;

  public TopicService(TopicRepository topicRepository, PostService postService) {
    this.topicRepository = topicRepository;
    this.postService = postService;
  }

  public Topic createTopic(CreateTopicDto createTopicDto) {
    Topic topic = new Topic();
    topic.setTitle(createTopicDto.getTitle());
    for (String postId : createTopicDto.getPostIds()) {
      Post post = postService.getPostById(postId);
      post.getTopics().add(topic);
      topic.getPosts().add(post);
    }

    topicRepository.save(topic);
    return topic;
  }

  //Performance bottleneck if we make query on all calls
  public List<Topic> getTopics() {
    return topicRepository.findAll();
  }

}
