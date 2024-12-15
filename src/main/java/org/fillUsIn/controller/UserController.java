package org.fillUsIn.controller;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.AuthenticationDTO;
import org.fillUsIn.dto.CreateUserDTO;
import org.fillUsIn.dto.LoginDTO;
import org.fillUsIn.dto.UserDTO;
import org.fillUsIn.entity.User;
import org.fillUsIn.security.JwtUtil;
import org.fillUsIn.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
  private final UserService userService;
  private final JwtUtil jwtUtil;
  public UserController(UserService userService, JwtUtil jwtUtil) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<User> logout() {
    userService.logout();
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationDTO> authenticate(@Validated @RequestBody LoginDTO loginDto) throws Exception {
    String token = userService.login(loginDto);

    return ResponseEntity.ok(new AuthenticationDTO(token));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserDTO>  createUser(@Validated @RequestBody CreateUserDTO createUserDto) throws Exception {
    final User newUser = userService.createUser(createUserDto);
    final String accessToken =  jwtUtil.generateJwtToken(newUser.getUsername());
    UserDTO userDTO = userService.convertToDto(newUser);
    userDTO.setAccessToken(accessToken);
    return ResponseEntity.ok(userDTO);
  }
}
