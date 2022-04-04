package com.example.backend.services;

import com.example.backend.configuration.security.TokenProvider;
import com.example.backend.dto.UserJwtDto;
import com.example.backend.dto.UserResponseDto;
import com.example.backend.dto.UserUpdateDto;
import com.example.backend.entities.User;
import com.example.backend.enums.Role;
import com.example.backend.exceptions.ApiException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final AuthenticationManager authenticationManager;

  /**
   * @param login user's login
   * @param password user's password
   * @return UserJwtDto user DTO with JWT token
   *     <p>Authenticate user
   */
  public UserJwtDto login(String login, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
      var user = userRepository.findByUsername(login);
      return UserMapper.toUserJwtDto(user, tokenProvider.createToken(login, user.getRoles()));

    } catch (AuthenticationException e) {
      throw new ApiException("Invalid login/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  /**
   * @param user user's login
   * @return UserJwtDto user DTO with JWT token
   *     <p>Register new user
   */
  public UserJwtDto register(User user) {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new ApiException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
    if (Objects.isNull(user.getRoles())) {
      user.setRoles(List.of(Role.ROLE_CLIENT));
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return UserMapper.toUserJwtDto(
        user, tokenProvider.createToken(user.getUsername(), user.getRoles()));
  }

  /**
   * @param login username
   *
   * Delete user by username
   */
  public void delete(String login) {
    userRepository.deleteByUsername(login);
  }

  /**
   * @param login username
   * @return User entity
   *
   * get User info by username
   */
  public User getUserInfo(String login) {
    return userRepository
        .getByUsername(login)
        .orElseThrow(() -> new ApiException("The user doesn't exist", HttpStatus.NOT_FOUND));
  }

  /**
   * @param request
   * @return User entity
   *
   * get current user information
   */
  public User getSelfInfo(HttpServletRequest request) {
    return userRepository.findByUsername(
        tokenProvider.getLogin(tokenProvider.resolveToken(request)));
  }

  /**
   * @param login username
   * @return JWT token
   *
   * refresh token by username
   */
  public String refreshToken(String login) {
    return tokenProvider.createToken(login, userRepository.findByUsername(login).getRoles());
  }

  /**
   * @param login potential username
   * @return True/False
   *
   * check if username is available
   */
  public boolean loginExists(String login) {
    return userRepository.existsByUsername(login);
  }

  /**
   * @param req http request
   * @param dto UserUpdateDto
   * @return User Entity
   *
   * update current user
   */
  public User update(HttpServletRequest req, UserUpdateDto dto) {
    var user =
        userRepository.findByUsername(tokenProvider.getLogin(tokenProvider.resolveToken(req)));
    user.setAddress(dto.getAddress());
    user.setBirthDate(dto.getBirthDate());
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setDescription(dto.getDescription());
    user.setRoles(dto.getRoles());
    return userRepository.save(user);
  }

  /**
   * @param username
   * @param dto UserUpdateDto
   * @return User Entity
   *
   * update User by username
   */
  public User updateByUsername(String username, UserUpdateDto dto) {
    var user = userRepository.findByUsername(username);
    user.setAddress(dto.getAddress());
    user.setBirthDate(dto.getBirthDate());
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setDescription(dto.getDescription());
    user.setRoles(dto.getRoles());
    return userRepository.save(user);
  }

  /**
   * @return List UserResponseDto
   *
   * Get all users
   */
  public List<UserResponseDto> getAll() {
    return userRepository.findAll().stream().map(UserMapper::toResponsetDto).collect(toList());
  }
}
