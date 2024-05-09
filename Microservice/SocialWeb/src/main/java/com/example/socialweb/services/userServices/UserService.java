package com.example.socialweb.services.userServices;

import com.example.socialweb.enums.ProfileCloseType;
import com.example.socialweb.enums.UserRole;
import com.example.socialweb.enums.UserSex;
import com.example.socialweb.exceptions.WrongDataException;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.RegisterModel;
import com.example.socialweb.repositories.UserRepository;
import com.example.socialweb.services.converters.UserConverter;
import com.example.socialweb.services.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
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
    private final RedisTemplate<String, Object> redisTemplate;
    private final String HASH_KEY = "user";

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
    public void registration(RegisterModel registerModel, PasswordEncoder passwordEncoder) throws WrongDataException {
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
            throw new WrongDataException("User data is invalid or the email is busy.");
        }
    }

    @Transactional
    public List<User> getAllUsers() throws Exception {
        List<Object> usersHash = redisTemplate.opsForHash().values(HASH_KEY);
        if (usersHash.isEmpty()) {
            List<User> users = userRepository.findAll();
            if (!users.isEmpty()) {
                users.forEach(u -> redisTemplate.opsForHash().put(HASH_KEY, u.getId().toString(), UserConverter.serializeUserToJsonString(u)));
                return users;
            }
            throw new Exception("No one user is found.");
        }
        return usersHash.stream().map(h -> UserConverter.serializeJsonStringToUser((String) h)).toList();
    }

    @Transactional
    public User getUserById(Long id) throws WrongDataException {
        String userHash = (String) redisTemplate.opsForHash().get(HASH_KEY, id.toString());
        if (userHash == null) {
            User user = userRepository.findUserById(id);
            if (user != null) {
                redisTemplate.opsForHash().put(HASH_KEY, id.toString(), UserConverter.serializeUserToJsonString(user));
                return user;
            }
            throw new WrongDataException(String.format("User with id %s not found.", id));
        }
        return UserConverter.serializeJsonStringToUser(userHash);
    }
    public User getCurrentUser(){
        return UserConverter.serializeJsonStringToUser((String)redisTemplate.opsForValue().get("current"));
    }
}
