package org.fillUsIn.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
public class CreateUserDTO {

  @NotNull
  @Size(min = 3, max = 50)
  private String username;

  @NotNull
  @Size(min = 8, max = 50)
  private String password;

}
