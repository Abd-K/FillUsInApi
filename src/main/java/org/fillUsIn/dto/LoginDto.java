package org.fillUsIn.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class LoginDto {
  @NotNull
  private String username;
  @NotNull
  private String password;
}
