package com.example.socialweb.services.userServices;

import com.example.socialweb.configurations.utils.Cache;
import com.example.socialweb.models.entities.Friendship;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.responseModels.ProfileModel;
import com.example.socialweb.repositories.FriendshipRepository;
import com.example.socialweb.repositories.UserRepository;
import com.example.socialweb.services.converters.FriendshipConverter;
import com.example.socialweb.services.converters.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final Cache cache;

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
    public void friendRequest(Long senderId, Long recipientId) {
        User sender = userRepository.findUserById(senderId);
        User recipient = userRepository.findUserById(recipientId);
        if (containsBySenderAndRecipientAndStatus(sender, recipient, false) || containsBySenderAndRecipientAndStatus(recipient, sender, false))
            throw new RequestRejectedException("You or the user already sent the request.");
        else if (containsBySenderAndRecipientAndStatus(sender, recipient, true) || containsBySenderAndRecipientAndStatus(recipient, sender, true))
            throw new RequestRejectedException("This user is already your friend.");
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
    public void confirmRequest(Long senderId, Long recipientId) throws Exception {
        User sender = userRepository.findUserById(senderId);
        User recipient = userRepository.findUserById(recipientId);
        if (friendshipRepository.existsBySenderAndRecipientAndStatus(sender, recipient, false)) {
            recipient = userRepository.findUserById(recipient.getId());

            Friendship friendship = friendshipRepository.findBySenderAndRecipientAndStatus(sender, recipient, false);

            friendship.setStatus(true);
            sender.setFriendsCount(sender.getFriendsCount() + 1);
            recipient.setFriendsCount(recipient.getFriendsCount() + 1);

            friendshipRepository.save(friendship);
            userRepository.save(sender);
            userRepository.save(recipient);

            cache.loadUser(recipient);
        } else
            throw new Exception("You have not received a request from this user.");
    }

    @Transactional
    public void rejectRequest(Long id, Long recipientId) {
        User sender = userRepository.findUserById(id);
        User recipient = userRepository.findUserById(recipientId);
        if (friendshipRepository.existsBySenderAndRecipientAndStatus(sender, recipient, false)) {
            recipient = userRepository.findUserById(recipient.getId());
            Friendship friendship = friendshipRepository.findBySenderAndRecipientAndStatus(sender, recipient, false);
            friendshipRepository.delete(friendship);
        } else
            throw new RequestRejectedException("You have not received a request from this user.");
    }

    @Transactional
    public void removeFriend(Long senderId, Long recipientId) {
        Friendship friendship;
        User sender = userRepository.findUserById(senderId);
        User recipient = userRepository.findUserById(recipientId);
        if (friendshipRepository.existsBySenderAndRecipientAndStatus(sender, recipient, true)) {
            friendship = friendshipRepository.findBySenderAndRecipientAndStatus(sender, recipient, true);
            sender.setFriendsCount(sender.getFriendsCount() - 1);
            recipient.setFriendsCount(recipient.getFriendsCount() - 1);
            friendshipRepository.delete(friendship);
            userRepository.save(sender);
            userRepository.save(recipient);
        } else if (friendshipRepository.existsBySenderAndRecipientAndStatus(recipient, sender, true)) {
            friendship = friendshipRepository.findBySenderAndRecipientAndStatus(recipient, sender, true);
            sender.setFriendsCount(sender.getFriendsCount() - 1);
            recipient.setFriendsCount(recipient.getFriendsCount() - 1);
            friendshipRepository.delete(friendship);
            userRepository.save(sender);
            userRepository.save(recipient);
        } else {
            throw new RequestRejectedException("This user is not your friend.");
        }
        cache.loadUser(sender);
    }
}
