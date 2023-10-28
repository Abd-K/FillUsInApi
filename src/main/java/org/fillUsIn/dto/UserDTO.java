package org.fillUsIn.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

  private String accessToken;
  private String username;
  private LocalDateTime createdAt;
}
