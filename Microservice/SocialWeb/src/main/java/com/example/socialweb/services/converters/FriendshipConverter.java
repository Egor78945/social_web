package com.example.socialweb.services.converters;

import com.example.socialweb.models.entities.Friendship;
import com.example.socialweb.models.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FriendshipConverter {
    public static List<User> sendersToUsers(List<Friendship> list) {
        return list
                .stream()
                .map(Friendship::getSender)
                .collect(Collectors.toList());
    }

    public static List<User> friendshipToUserByUser(List<Friendship> friendships, User user) {
        return friendships.stream().map(e -> {
                    if (e.getSender().getId().equals(user.getId())) {
                        return e.getRecipient();
                    } else {
                        return e.getSender();
                    }
                })
                .collect(Collectors.toList());
    }
}
