package com.returntaxi.returntaxibe.user.dto;

import com.returntaxi.returntaxibe.user.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResDto {
    private String accessToken;
    private String refreshToken;
    private String email;
    private Role role;
}
