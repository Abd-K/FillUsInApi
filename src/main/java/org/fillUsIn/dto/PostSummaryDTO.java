package org.fillUsIn.dto;

import lombok.Data;
import org.fillUsIn.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostSummaryDTO {

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

}
