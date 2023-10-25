package org.fillUsIn.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreatePostDTO {

  @NotNull
  private String title;
  private String body;
  private String url;
}
