package com.example.socialweb.models.requestModels;

import com.example.socialweb.enums.roles.UserRole;
import com.example.socialweb.enums.sex.UserSex;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class RegisterModel {
    private String email;
    private String name;
    private String surname;
    private String password;
    private String sex;
}
