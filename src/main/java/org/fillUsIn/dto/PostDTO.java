package org.fillUsIn.dto;

import lombok.Getter;
import lombok.Setter;
import org.fillUsIn.entity.Comment;
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
  private List<Comment> comments;
  private List<String> userLikesUsernames;
  private List<String> userDislikesUsernames;

}
