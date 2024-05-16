package com.example.socialweb.models.factories;

import com.example.socialweb.enums.ProfileCloseType;
import com.example.socialweb.enums.UserRole;
import com.example.socialweb.enums.UserSex;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.RegisterModel;
import com.example.socialweb.services.converters.UserConverter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFactory {
    public User getUserForRegistration(RegisterModel registerModel, PasswordEncoder passwordEncoder){
        List<UserRole> roles = new ArrayList<>();

        roles.add(UserRole.USER);

        User user = new User
                .Builder(registerModel.getEmail(), passwordEncoder.encode(registerModel.getPassword()))
                .setName(registerModel.getName())
                .setSurname(registerModel.getSurname())
                .setRole(roles)
                .setSex(UserConverter.convertStringToSex(registerModel.getSex()))
                .setProfileCloseType(UserConverter.convertStringToProfileCloseType(registerModel.getProfileCloseType()))
                .build();

        return user;
    }
}
