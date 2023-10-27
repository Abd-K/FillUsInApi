package org.fillUsIn.dto;

import lombok.Getter;


@Getter
public class CreateCommentDTO {

  private String parentPostId;
  private String parentCommentId;
  private String body;
}
