package com.example.socialweb.models.requestModels;

import lombok.Data;

@Data
public class RegisterModel {
    private String email;
    private String name;
    private String surname;
    private String password;
    private String sex;
    private String profileCloseType;
}
