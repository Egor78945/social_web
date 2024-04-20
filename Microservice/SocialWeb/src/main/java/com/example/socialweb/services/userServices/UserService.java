package com.example.socialweb.services.userServices;

import com.example.socialweb.enums.ProfileCloseType;
import com.example.socialweb.enums.UserRole;
import com.example.socialweb.enums.UserSex;
import com.example.socialweb.exceptions.WrongUserDataException;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.RegisterModel;
import com.example.socialweb.repositories.UserRepository;
import com.example.socialweb.services.converters.UserConverter;
import com.example.socialweb.services.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        return userDetails.build(userRepository.findUserByEmail(username));
    }

    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    public boolean containsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Transactional
    public void registration(RegisterModel registerModel, PasswordEncoder passwordEncoder) {
        if (UserValidation.checkUserData(registerModel) && !containsUserByEmail(registerModel.getEmail())) {
            UserSex sex = UserConverter.convertStringToSex(registerModel.getSex());
            ProfileCloseType closeType = UserConverter.convertStringToProfileCloseType(registerModel.getProfileCloseType());
            List<UserRole> roles = new ArrayList<>();
            roles.add(UserRole.USER);
            User user = new User
                    .Builder(registerModel.getEmail(), passwordEncoder.encode(registerModel.getPassword()))
                    .setName(registerModel.getName())
                    .setSurname(registerModel.getSurname())
                    .setRole(roles)
                    .setSex(sex)
                    .setProfileCloseType(closeType)
                    .build();
            userRepository.save(user);
        } else {
            throw new WrongUserDataException("User data is invalid or the email is busy.");
        }
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }
}
