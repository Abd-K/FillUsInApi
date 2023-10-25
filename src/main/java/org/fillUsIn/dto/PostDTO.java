package org.fillUsIn.dto;

import lombok.Getter;
import lombok.Setter;
import org.fillUsIn.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostDTO {

  private String id;
  private String subcategoryName;
  private String categoryName;
  private String title;
  private String body;
  private int voteCount;
  private LocalDateTime createdAt;
  private LocalDateTime updatedDate;
  private User user;
  private String url;
  private String thumbnailUrl;
  private List<String> userLikesUsernames; // List of usernames who liked the post
  private List<String> userDislikesUsernames; // List of usernames who disliked the post

}
