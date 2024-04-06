package com.example.socialweb.configurations.utils;

import com.example.socialweb.models.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

public class ServerUtils {
    public static User getUserFromSession(HttpServletRequest request){
        return (User)request.getSession().getAttribute("user");
    }
}
