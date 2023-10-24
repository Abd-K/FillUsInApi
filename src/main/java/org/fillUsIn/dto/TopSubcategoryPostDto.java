package org.fillUsIn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fillUsIn.entity.Post;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSubcategoryPostDto {
  private String subcategoryName;
  private Post post;
}
