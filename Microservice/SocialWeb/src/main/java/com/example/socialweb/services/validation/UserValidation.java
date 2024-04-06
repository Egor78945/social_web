package com.example.socialweb.services.validation;

import com.example.socialweb.models.requestModels.RegisterModel;

public class UserValidation {
    private static boolean checkEmailValid(String email) {
        if (email.length() > 18 && email.length() < 150 && email.endsWith("@gmail.com")) {
            String pre = email.substring(0, email.length() - 11);
            for (char i : pre.toCharArray()) {
                if (!Character.isLetter(i) && !Character.isDigit(i)) {
                    return false;
                }
            }
            return true;
        } else if (email.length() > 18 && email.length() < 150 && email.endsWith("@mail.ru")) {
            String pre = email.substring(0, email.length() - 9);
            for (char i : pre.toCharArray())
                if (!Character.isLetter(i) && !Character.isDigit(i)) {
                    return false;
                }
            return true;
        }
        return false;
    }

    private static boolean checkNSValid(String ns) {
        if (ns.length() > 2 && ns.length() < 20) {
            for (char i : ns.toCharArray()) {
                if (!Character.isLetter(i) && !Character.isDigit(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean checkPasswordValid(String password) {
        if (password.length() > 8 && password.length() < 20) {
            int dig = 0;
            int let = 0;
            for (char i : password.toCharArray()) {
                if (Character.isDigit(i))
                    dig++;
                else if (Character.isLetter(i))
                    let++;
                else
                    return false;
            }
            if (dig < 4 || let < 4)
                return false;
            return true;
        }
        return false;
    }

    private static boolean checkSexValid(String sex) {
        return sex.equalsIgnoreCase("man") || sex.equalsIgnoreCase("woman");
    }
    private static boolean checkCloseProfileTypeValid(String type){
        return type.equalsIgnoreCase("close") || type.equalsIgnoreCase("open");
    }

    public static boolean checkUserData(RegisterModel model) {
        return checkEmailValid(model.getEmail())
                && checkNSValid(model.getName())
                && checkNSValid(model.getSurname())
                && checkSexValid(model.getSex())
                && checkPasswordValid(model.getPassword())
                && checkCloseProfileTypeValid(model.getProfileCloseType());
    }
}
