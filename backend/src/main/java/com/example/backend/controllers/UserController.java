package com.example.backend.controllers;

import javax.servlet.http.HttpServletRequest;

import com.example.backend.dto.UserResponseDto;
import com.example.backend.dto.UserUpdateDto;
import com.example.backend.mappers.UserMapper;
import com.example.backend.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

  /**
   * @param username
   * @return Deleted username
   *
   * Delete user by username
   */
  @DeleteMapping(value = "/{username}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 404, message = "The user doesn't exist"),
        @ApiResponse(code = 500, message = "Expired or invalid JWT token")
      })
  public String delete(@ApiParam("Username") @PathVariable String username) {
        userService.delete(username);
        return username;
    }

  /**
   * @param req http request
   * @param user User Update Dto
   * @return User response dto
   *
   * Update current user information
   */
  @PutMapping("/update")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 422, message = "Username is already in use")
      })
  public UserResponseDto update(HttpServletRequest req, @RequestBody UserUpdateDto user) {
        return UserMapper.toResponsetDto(userService.update(req, user));
    }

  /**
   * @param username login
   * @param user User update dto
   * @return User response dto
   *
   * Update user by username, Admin Panel
   */
  @PutMapping("/update/{username}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 422, message = "Username is already in use")
      })
  public UserResponseDto updateByUsername(
      @PathVariable String username, @RequestBody UserUpdateDto user) {
        return UserMapper.toResponsetDto(userService.updateByUsername(username, user));
    }

  /**
   * @param username
   * @return User response dto
   *
   * Get User information by username
   */
  @GetMapping(value = "/{username}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"), //
        @ApiResponse(code = 403, message = "Access denied"), //
        @ApiResponse(code = 404, message = "The user doesn't exist"), //
        @ApiResponse(code = 500, message = "Expired or invalid JWT token")
      })
  public UserResponseDto getUserInfo(@ApiParam("Username") @PathVariable String username) {
        return UserMapper.toResponsetDto(userService.getUserInfo(username));
    }

  /**
   * @param req http request
   * @return User response dto
   *
   * get current user information
   */
  @GetMapping(value = "/whoami")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Expired or invalid JWT token")
      })
  public UserResponseDto getSelfInfo(HttpServletRequest req) {
        return UserMapper.toResponsetDto(userService.getSelfInfo(req));
    }

  /**
   * @return List response users dtos
   *
   * Get All users for admin panel
   */
  @GetMapping("/all")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

  /**
   * @param username
   * @return True/False
   *
   * Check if username is available for registration
   */
  @GetMapping("/valid/{username}")
  public boolean valid(@PathVariable String username) {
        return !userService.loginExists(username);
    }
}

