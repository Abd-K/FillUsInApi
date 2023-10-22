package org.fillUsIn.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class CreatePostDto {

  @NonNull
  private String title;

  private String body;
  @NonNull
  private String username; //STUBBED

  private String url;

}
