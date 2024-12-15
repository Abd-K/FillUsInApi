package org.fillUsIn.dto;

import lombok.Data;
import org.fillUsIn.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {

  private String id;
  private String subcategoryName;
  private String categoryName;
  private String title;
  private String body;
  private int voteCount;
  private int commentCount;
  private LocalDateTime createdAt;
  private LocalDateTime updatedDate;
  private String username;
  private String url;
  private String thumbnailUrl;
  private List<Comment> comments;
  private List<String> userLikesUsernames;
  private List<String> userDislikesUsernames;

}
