package com.example.socialweb.services.converters;

import com.example.socialweb.enums.ProfileCloseType;
import com.example.socialweb.enums.UserSex;
import com.example.socialweb.exceptions.WrongUserDataException;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.responseModels.ProfileModel;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserConverter {
    public static UserSex convertStringToSex(String sex) {
        if (sex.equalsIgnoreCase("man"))
            return UserSex.MAN;
        else if (sex.equalsIgnoreCase("woman"))
            return UserSex.WOMAN;
        else
            throw new WrongUserDataException("This sex type is unknown.");

    }

    public static String convertSexToString(UserSex sex) {
        if (sex == UserSex.MAN)
            return "man";
        else
            return "woman";
    }

    public static ProfileModel convertUserToProfileModel(User user) {
        return new ProfileModel(user);
    }

    public static List<ProfileModel> convertUserToProfileModel(List<User> list) {
        List<ProfileModel> resultList = new ArrayList<>();
        for (User u : list) {
            resultList.add(convertUserToProfileModel(u));
        }
        return resultList;
    }

    public static ProfileCloseType convertStringToProfileCloseType(String type) {
        if (type.equalsIgnoreCase(ProfileCloseType.CLOSE.name()))
            return ProfileCloseType.CLOSE;
        else if (type.equalsIgnoreCase(ProfileCloseType.OPEN.name()))
            return ProfileCloseType.OPEN;
        else
            throw new WrongUserDataException("This close type is unknown.");
    }

    public static String convertProfileCloseTypeToString(ProfileCloseType closeType) {
        if (closeType == ProfileCloseType.CLOSE)
            return "close";
        else
            return "open";
    }

    public static byte[] serializeUser(User user) {
        byte[] arr = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(user);
            arr = outputStream.toByteArray();
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return arr;
    }

    public static User deserializeUser(byte[] user) {
        User userOut = null;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(user);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            userOut = (User) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            log.info(e.getMessage());
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return userOut;
    }

}
