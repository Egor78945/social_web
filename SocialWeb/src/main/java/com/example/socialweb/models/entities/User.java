package com.example.socialweb.models.entities;

import com.example.socialweb.enums.ProfileCloseType;
import com.example.socialweb.enums.UserRole;
import com.example.socialweb.enums.UserSex;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode
public class User implements Comparable<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private List<UserRole> role;
    @Column(name = "friends_count")
    private Integer friendsCount;
    @Column(name = "sex")
    private UserSex sex;
    @Column(name = "register_date")
    private String registerDate;
    @Column(name = "close_type")
    private ProfileCloseType closeType;

    private User(Builder builder) {
        this.email = builder.userEmail;
        this.name = builder.userName;
        this.surname = builder.userSurname;
        this.password = builder.userPassword;
        this.role = builder.userRole;
        this.sex = builder.userSex;
        this.friendsCount = 0;
        this.registerDate = new Date(System.currentTimeMillis()).toString();
        this.closeType = builder.closeType;
    }

    public User() {
        this.registerDate = new Date(System.currentTimeMillis()).toString();
        this.friendsCount = 0;
        this.role = new ArrayList<>();
    }

    @Override
    public int compareTo(User o) {
        return this.name.compareTo(o.getName());
    }

    public static class Builder {
        private String userEmail;
        private String userName;
        private String userSurname;
        private String userPassword;
        private UserSex userSex;
        private List<UserRole> userRole;
        private ProfileCloseType closeType;

        public Builder(String email, String password) {
            userEmail = email;
            userPassword = password;
        }

        public Builder setName(String name) {
            userName = name;
            return this;
        }

        public Builder setSurname(String surname) {
            userSurname = surname;
            return this;
        }

        public Builder setSex(UserSex sex) {
            userSex = sex;
            return this;
        }

        public Builder setRole(List<UserRole> list) {
            userRole = list;
            return this;
        }

        public Builder setProfileCloseType(ProfileCloseType type) {
            closeType = type;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public boolean isBan() {
        return this.role.isEmpty();
    }
}
