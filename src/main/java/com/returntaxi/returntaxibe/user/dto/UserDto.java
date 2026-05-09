package com.returntaxi.returntaxibe.user.dto;

import com.returntaxi.returntaxibe.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    private static final long serialVersionUID = -770983907612901054L;

    private Long userId;
    private String email;
    private Role role;
    private String firstName;
    private String lastName;
    private String userName;
    private String address;
    private String idNo;
    private String phoneNo;

    // Driver specific fields
    private String vehicleNo;
    private String vehicleType;
    private String vehicleBrand;
    private List<String> vehiclePhotos;
    private List<String> licensePhotos;
}
