package com.example.backend.controllers;

import com.example.backend.dto.UserJwtDto;
import com.example.backend.dto.UserRegisterDto;
import com.example.backend.dto.UserSignInDto;
import com.example.backend.mappers.UserMapper;
import com.example.backend.services.UserService;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;

  /**
   * @param dto Sign in dto (login & password)
   * @return User DTO with JWT
   *     <p>authenticate user
   */
  @PostMapping("/login")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 422, message = "Invalid username/password supplied")
      })
  public UserJwtDto login(@RequestBody UserSignInDto dto) {
    return userService.login(dto.getUsername(), dto.getPassword());
  }

  /**
   * @param user Registration Dto
   * @return User Dto with Jwt token
   *
   * Register new user
   */
  @PostMapping("/register")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 422, message = "Username is already in use")
      })
  public UserJwtDto signup(@ApiParam("Signup User") @RequestBody UserRegisterDto user) {
    return userService.register(UserMapper.toEntity(user));
  }

  @GetMapping("/refresh")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  public String refresh(HttpServletRequest req) {
    return userService.refreshToken(req.getRemoteUser());
  }
}
