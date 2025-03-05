package com.bosorio.user_service.infrastructure.adapter.in.controller;

import com.bosorio.user_service.application.dto.LoginUserDto;
import com.bosorio.user_service.application.dto.RegisterUserDto;
import com.bosorio.user_service.application.dto.UserDto;
import com.bosorio.user_service.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserDto userDto) {
        String token = userService.login(userDto);

        Map<String, String> map = new HashMap<>();
        map.put("message", "User logged in successfully");
        map.put("token", token);

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDto userDto) {
        userService.register(userDto);

        Map<String, String> map = new HashMap<>();
        map.put("message", "User registered successfully");

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @GetMapping("/users/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.findUserById(id);
    }

}
