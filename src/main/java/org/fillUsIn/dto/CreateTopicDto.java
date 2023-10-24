package org.fillUsIn.dto;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
public class CreateTopicDto {

  @NonNull
  private String title;
  @NonNull
  private List<String> pickedPostIds;
  @NonNull
  private List<String> subcategoryNamesTopPick;

}
