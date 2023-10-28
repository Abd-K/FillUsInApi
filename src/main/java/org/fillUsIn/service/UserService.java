package org.fillUsIn.service;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreateUserDTO;
import org.fillUsIn.dto.LoginDTO;
import org.fillUsIn.dto.UserDTO;
import org.fillUsIn.dto.mapper.UserMapper;
import org.fillUsIn.entity.User;
import org.fillUsIn.repository.UserRepository;
import org.fillUsIn.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;


  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = getUser(username);
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }

  public User createUser(CreateUserDTO createUserDto) throws Exception {
    if(usernameExists(createUserDto.getUsername())) {
      throw new Exception("Username already exists");
    } else {
      User newUser = new User();
      newUser.setUsername(createUserDto.getUsername());
      newUser.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
      return userRepository.saveAndFlush(newUser);
    }
  }

  public String login(LoginDTO loginDto) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
      return jwtUtil.generateJwtToken(loginDto.getUsername());
    } catch (BadCredentialsException e) {
      throw new Exception("Username or password incorrect");
    }
  }

  public UserDTO convertToDto(User user) {
    return UserMapper.INSTANCE.userToUserDTO(user);
  }

  public void logout() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      SecurityContextHolder.getContext().setAuthentication(null);
    }
  }

  private boolean usernameExists(String username) {
    return getByUsername(username).isPresent();
  }

  public User getUser(String username) {
    return getByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
  }

  private Optional<User> getByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
