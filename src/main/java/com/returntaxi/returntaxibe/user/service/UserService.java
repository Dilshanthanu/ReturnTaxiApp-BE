package com.returntaxi.returntaxibe.user.service;

import com.returntaxi.returntaxibe.user.dto.UserDto;
import com.returntaxi.returntaxibe.user.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    User registerUser(User user);

    User update(User user);

    void assignRolesToUser(Long userId, Set<Long> roleIds);

    void deleteUser(Long userId);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

}
