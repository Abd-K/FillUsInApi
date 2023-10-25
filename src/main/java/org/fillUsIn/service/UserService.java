package org.fillUsIn.service;

import lombok.extern.slf4j.Slf4j;
import org.fillUsIn.dto.CreateUserDTO;
import org.fillUsIn.dto.LoginDTO;
import org.fillUsIn.entity.User;
import org.fillUsIn.repository.UserRepository;
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
  private final AuthenticationManager authenticationManager;


  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
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
      Authentication authentication = new UsernamePasswordAuthenticationToken(createUserDto.getUsername(), createUserDto.getPassword());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return userRepository.saveAndFlush(newUser);
    }
  }

  private void authenticateUser(String username, String password) {
    Authentication authenticatedToken = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
  }

  public User login(LoginDTO loginDto) throws Exception {
    try {
      authenticateUser(loginDto.getUsername(), loginDto.getPassword());

    } catch (BadCredentialsException e) {
      throw new Exception("Username or password incorrect");
    }
    return getUser(loginDto.getUsername());
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
