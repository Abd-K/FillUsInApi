package org.fillUsIn.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class CreateTopicDTO {

  @NotNull
  private String title;
  @NotNull
  private List<String> pickedPostIds;
  @NotNull
  private List<String> subcategoryNamesTopPick;

}
