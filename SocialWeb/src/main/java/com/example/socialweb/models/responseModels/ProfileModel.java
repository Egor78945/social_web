package com.example.socialweb.models.responseModels;

import com.example.socialweb.models.entities.User;
import com.example.socialweb.services.converters.UserConverter;
import lombok.Data;

@Data
public class ProfileModel {
    private Long id;
    private String name;
    private String surname;
    private Integer friendsCount;
    private String sex;
    private String registerDate;
    private String profileCloseType;
    public ProfileModel(User user){
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
        friendsCount = user.getFriendsCount();
        sex = UserConverter.convertSexToString(user.getSex());
        registerDate = user.getRegisterDate();
        profileCloseType = UserConverter.convertProfileCloseTypeToString(user.getCloseType());
    }
}
