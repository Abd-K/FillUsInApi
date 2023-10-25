package org.fillUsIn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicWithTopPostsDTO {
  private String title;
  private List<TopSubcategoryPostDTO> topPosts;

}
