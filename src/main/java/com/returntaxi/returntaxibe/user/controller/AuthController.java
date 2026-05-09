package com.returntaxi.returntaxibe.user.controller;

import com.returntaxi.returntaxibe.security.JwtUtil;
import com.returntaxi.returntaxibe.user.dto.AuthResDto;
import com.returntaxi.returntaxibe.user.dto.LoginReqDto;
import com.returntaxi.returntaxibe.user.entity.User;
import com.returntaxi.returntaxibe.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(
            @RequestBody LoginReqDto loginReqDto,
            HttpServletRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginReqDto.getUserName(),
                        loginReqDto.getPassword()));

        UserDetails userDetails = userService.loadUserByUsername(loginReqDto.getUserName());
        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        User user = (User) userDetails;

        return ResponseEntity.ok(
                new AuthResDto(accessToken, refreshToken, user.getEmail(), user.getRole()));
    }

}
