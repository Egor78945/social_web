package com.example.socialweb.services.userServices;

import com.example.socialweb.exceptions.RequestCancelledException;
import com.example.socialweb.models.entities.Friendship;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.responseModels.ProfileModel;
import com.example.socialweb.repositories.FriendshipRepository;
import com.example.socialweb.repositories.UserRepository;
import com.example.socialweb.services.converters.UserConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<ProfileModel> getAllFriendRequests(Long userId, boolean status) {
        User user = userRepository.findUserById(userId);
        return allByRecipientAndStatus(user, status)
                .stream()
                .map(e -> UserConverter
                        .convertUserToProfileModel(e.getSender()))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean containsBySenderAndRecipientAndStatus(User sender, User recipient, Boolean status) {
        return friendshipRepository.existsBySenderAndRecipientAndStatus(sender, recipient, status);
    }

    @Transactional
    public void friendRequest(Long senderId, Long recipientId) throws RequestCancelledException {
        User sender = userRepository.findUserById(senderId);
        User recipient = userRepository.findUserById(recipientId);
        if (containsBySenderAndRecipientAndStatus(sender, recipient, false) || containsBySenderAndRecipientAndStatus(recipient, sender, false))
            throw new RequestCancelledException("You or the user already sent the request.");
        else if (containsBySenderAndRecipientAndStatus(sender, recipient, true) || containsBySenderAndRecipientAndStatus(recipient, sender, true))
            throw new RequestCancelledException("This user is already your friend.");
        else {
            Friendship friendship = new Friendship(sender, recipient);
            friendshipRepository.save(friendship);
        }
    }

    @Transactional
    public List<Friendship> allByRecipientAndStatus(User recipient, boolean status) {
        List<Friendship> list = friendshipRepository.findAllByRecipientAndStatus(recipient, status);
        list.addAll(friendshipRepository.findAllBySenderAndStatus(recipient, status));
        return list;
    }

    @Transactional
    public void confirmRequest(Long senderId, Long recipientId) throws RequestCancelledException {
        User sender = userRepository.findUserById(senderId);
        User recipient = userRepository.findUserById(recipientId);
        if (friendshipRepository.existsBySenderAndRecipientAndStatus(sender, recipient, false)) {
            Friendship friendship = friendshipRepository.findBySenderAndRecipientAndStatus(sender, recipient, false);

            friendship.setStatus(true);
            sender.setFriendsCount(sender.getFriendsCount() + 1);
            recipient.setFriendsCount(recipient.getFriendsCount() + 1);

            friendshipRepository.save(friendship);
            userRepository.save(sender);
            userRepository.save(recipient);
        } else
            throw new RequestCancelledException("You have not received a request from this user.");
    }

    @Transactional
    public void rejectRequest(Long senderId, Long recipientId) throws RequestCancelledException {
        User sender = userRepository.findUserById(senderId);
        User recipient = userRepository.findUserById(recipientId);
        if (friendshipRepository.existsBySenderAndRecipientAndStatus(sender, recipient, false)) {
            Friendship friendship = friendshipRepository.findBySenderAndRecipientAndStatus(sender, recipient, false);
            friendshipRepository.delete(friendship);
        } else
            throw new RequestCancelledException("You have not received a request from this user.");
    }

    @Transactional
    public void removeFriend(Long senderId, Long recipientId) throws RequestCancelledException {
        Friendship friendship;
        User sender = userRepository.findUserById(senderId);
        User recipient = userRepository.findUserById(recipientId);
        if (friendshipRepository.existsBySenderAndRecipientAndStatus(sender, recipient, true)) {
            friendship = friendshipRepository.findBySenderAndRecipientAndStatus(sender, recipient, true);
        } else if (friendshipRepository.existsBySenderAndRecipientAndStatus(recipient, sender, true)) {
            friendship = friendshipRepository.findBySenderAndRecipientAndStatus(recipient, sender, true);
        } else {
            throw new RequestCancelledException("This user is not your friend.");
        }
        sender.setFriendsCount(sender.getFriendsCount() - 1);
        recipient.setFriendsCount(recipient.getFriendsCount() - 1);

        friendshipRepository.delete(friendship);

        userRepository.save(sender);
        userRepository.save(recipient);
    }
}
