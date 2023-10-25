package org.fillUsIn.controller;


import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreateTopicDTO;
import org.fillUsIn.dto.TopicWithTopPostsDTO;
import org.fillUsIn.entity.Topic;
import org.fillUsIn.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topics")
@Slf4j
public class TopicController {

  private final TopicService topicService;

  public TopicController(TopicService topicService) {
    this.topicService = topicService;
  }

  @GetMapping()
  @ResponseStatus(HttpStatus.ACCEPTED)
  public List<TopicWithTopPostsDTO> getTopics() {
    return topicService.getAllTopicsWithTopPosts();
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public Topic createTopic(@Validated @RequestBody CreateTopicDTO createTopicDto) {
    return topicService.createTopic(createTopicDto);
  }
}
