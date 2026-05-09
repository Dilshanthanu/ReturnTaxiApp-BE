package com.returntaxi.returntaxibe.user.service.impl;

import com.returntaxi.returntaxibe.user.dto.UserDto;
import com.returntaxi.returntaxibe.user.entity.User;
import com.returntaxi.returntaxibe.user.repository.UserRepository;
import com.returntaxi.returntaxibe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void assignRolesToUser(Long userId, Set<Long> roleIds) {
        // Implementation for role assignment if needed
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getRole())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .address(user.getAddress())
                .idNo(user.getIdNo())
                .phoneNo(user.getPhoneNo())
                .vehicleNo(user.getVehicleNo())
                .vehicleType(user.getVehicleType())
                .vehicleBrand(user.getVehicleBrand())
                .vehiclePhotos(user.getVehiclePhotos())
                .licensePhotos(user.getLicensePhotos())
                .build();
    }
}
