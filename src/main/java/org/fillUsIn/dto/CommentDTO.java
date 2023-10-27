package org.fillUsIn.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommentDTO {

  private String id;
  private String body;
  private int voteCount;
  private LocalDateTime createdAt;
  private LocalDateTime updatedDate;
  private String username;
  private String url;
  private List<CommentDTO> replyComments;

}
