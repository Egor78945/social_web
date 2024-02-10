package com.example.socialweb.models.entities;

import com.example.socialweb.enums.roles.UserRole;
import com.example.socialweb.enums.sex.UserSex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode
public class User {
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

    private User(Builder builder) {
        this.email = builder.userEmail;
        this.name = builder.userName;
        this.surname = builder.userSurname;
        this.password = builder.userPassword;
        this.role = builder.userRole;
        this.sex = builder.userSex;
        this.friendsCount = 0;
        this.registerDate = new Date(System.currentTimeMillis()).toString();
    }

    public User() {

    }

    public static class Builder {
        private String userEmail;
        private String userName;
        private String userSurname;
        private String userPassword;
        private UserSex userSex;
        private List<UserRole> userRole;

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

        public User build() {
            return new User(this);
        }
    }
}
