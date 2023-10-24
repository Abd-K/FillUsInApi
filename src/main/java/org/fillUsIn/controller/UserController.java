package org.fillUsIn.controller;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreateUserDto;
import org.fillUsIn.dto.LoginDto;
import org.fillUsIn.entity.User;
import org.fillUsIn.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<User> login(@Validated @RequestBody LoginDto loginDto) throws Exception {
    User user = userService.login(loginDto);
    return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public User createPost(@Validated @RequestBody CreateUserDto createUserDto) throws Exception {
    return userService.createUser(createUserDto);
  }
}
