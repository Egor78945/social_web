package com.example.socialweb.models.requestModels;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class RegisterModel {
    private String email;
    private String name;
    private String surname;
    private String password;
    private String sex;
    private String profileCloseType;
}
