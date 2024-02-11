package com.example.socialweb.services.converters;

import com.example.socialweb.models.entities.Friendship;
import com.example.socialweb.models.entities.User;

import java.util.ArrayList;
import java.util.List;

public class FriendshipConverter {
    public static List<User> sendersToUsers(List<Friendship> list){
        List<User> result = new ArrayList<>();
        for(Friendship f: list){
            result.add(f.getSender());
        }
        return result;
    }

    public static List<User> friendshipToUserByUser(List<Friendship> friendships, User user) {
        List<User> list = new ArrayList<>();
        for(Friendship f: friendships){
            if(f.getSender().getId().equals(user.getId()))
                list.add(f.getRecipient());
             else
                 list.add(f.getSender());
        }
        return list;
    }
}
