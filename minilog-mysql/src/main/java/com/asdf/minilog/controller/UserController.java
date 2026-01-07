package com.asdf.minilog.controller;

import com.asdf.minilog.dto.UserRequestDto;
import com.asdf.minilog.dto.UserResponseDto;
import com.asdf.minilog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @Operation(summary = "사용자 목록 조회")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "성공")})
  public ResponseEntity<Iterable<UserResponseDto>> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  @GetMapping("/{userId}")
  @Operation(summary = "사용자 조회")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
    @ApiResponse(responseCode = "404", description = "사용자 없음")
  })
  public ResponseEntity<UserResponseDto> getUserById(@PathVariable("userId") Long userId) {
    Optional<UserResponseDto> user = userService.getUserById(userId);
    return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  @Operation(summary = "사용자 생성")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "성공")})
  public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto request) {
    UserResponseDto createdUser = userService.createUser(request);
    return ResponseEntity.ok(createdUser);
  }

  @PutMapping("/{userId}")
  @Operation(summary = "사용자 생성")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
    @ApiResponse(responseCode = "404", description = "사용자 없음")
  })
  public ResponseEntity<UserResponseDto> updateUser(
      @PathVariable("userId") Long userId, @RequestBody UserRequestDto request) {
    UserResponseDto userResponseDto = userService.updateUser(userId, request);
    return ResponseEntity.ok(userResponseDto);
  }

  @DeleteMapping("/{userId}")
  @Operation(summary = "사용자 삭제")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "성공"),
    @ApiResponse(responseCode = "404", description = "사용자 없음")
  })
  public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
