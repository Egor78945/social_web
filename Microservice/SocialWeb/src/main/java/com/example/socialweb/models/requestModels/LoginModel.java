package com.example.socialweb.models.requestModels;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class LoginModel {
    private String email;
    private String password;
}
