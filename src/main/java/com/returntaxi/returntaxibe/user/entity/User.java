package com.returntaxi.returntaxibe.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = -3157443083568594190L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_USERS")
    @SequenceGenerator(sequenceName = "APP_USERS_SEQ", allocationSize = 1, name = "APP_USERS")
    @Column(name = "ID", nullable = false)
    private Long userId;

    @Column(name = "EMAIL", unique = true)
    @NotEmpty(message = "{entity.Staff.email.notEmpty}")
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "FIRST_NAME", length = 200)
    @NotEmpty(message = "{entity.Staff.firstName.notEmpty}")
    private String firstName;

    @Column(name = "LAST_NAME", length = 200)
    @NotEmpty(message = "{entity.Staff.lastName.notEmpty}")
    private String lastName;

    @Column(name = "USER_NAME")
    @NotEmpty(message = "{entity.Staff.userName.notEmpty}")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "ADDRESS", length = 200)
    @NotEmpty(message = "{entity.Staff.address.notEmpty}")
    private String address;

    @Column(name = "ID_NO", length = 20)
    @NotEmpty(message = "{entity.Staff.idNo.notEmpty}")
    private String idNo;

    @Column(name = "PHONE_NO")
    @NotEmpty(message = "{entity.Staff.phoneNo.notEmpty}")
    private String phoneNo;

    @Column(name = "VEHICLE_NO")
    private String vehicleNo;

    @Column(name = "VEHICLE_TYPE")
    private String vehicleType;

    @Column(name = "VEHICLE_BRAND")
    private String vehicleBrand;

    @ElementCollection
    @CollectionTable(name = "vehicle_photos", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "photo_url")
    private List<String> vehiclePhotos;

    @ElementCollection
    @CollectionTable(name = "license_photos", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "photo_url")
    private List<String> licensePhotos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
