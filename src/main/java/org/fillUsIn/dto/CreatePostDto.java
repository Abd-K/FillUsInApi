package org.fillUsIn.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class CreatePostDto {

  @NonNull
  private String title;
  @NonNull
  private String body;
  @NonNull
  private String username; //STUBBED

}
