package org.project.subscriptionservice.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.project.subscriptionservice.bean.UserEntity;
import org.project.subscriptionservice.bean.enums.AccountStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Data
@AllArgsConstructor
public class UserDetail implements UserDetails {

    private Integer id;
    private String username;
    private String email;
    private String phone;
    private String job;
    private Integer locked;
    private AccountStatus active;
//    private int gender;

    @JsonIgnore
    private String password;
    private Date createdAt;
    private String createdBy;

    public static UserDetail build(UserEntity user) {
        return new UserDetail(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPhone(),
            user.getJob(),
            user.getLocked(),
            user.getActive(),
            user.getPassword(),
            user.getCreatedAt(),
            user.getCreatedBy()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
