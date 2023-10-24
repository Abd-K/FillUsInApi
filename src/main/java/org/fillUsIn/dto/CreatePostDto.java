package org.fillUsIn.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreatePostDto {

  @NotNull
  private String title;
  @NotNull
  private String username; //STUBBED
  private String body;
  private String url;
}
