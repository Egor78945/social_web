package com.example.socialweb.services.userServices;

import com.example.socialweb.enums.UserRole;
import com.example.socialweb.enums.UserSex;
import com.example.socialweb.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String password;
    private List<UserRole> role;
    private Integer friendsCount;
    private UserSex sex;
    private String registerDate;

    public UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                id = user.getId(),
                email = user.getEmail(),
                name = user.getName(),
                surname = user.getSurname(),
                password = user.getPassword(),
                role = user.getRole(),
                friendsCount = user.getFriendsCount(),
                sex = user.getSex(),
                registerDate = user.getRegisterDate()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
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
