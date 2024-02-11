package com.example.socialweb.services;

import com.example.socialweb.models.entities.Friendship;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.repositories.FriendshipRepository;
import com.example.socialweb.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean containsBySenderAndRecipientAndStatus(User sender, User recipient, Boolean status) {
        return friendshipRepository.existsBySenderAndRecipientAndStatus(sender, recipient, status);
    }

    @Transactional
    public void friendRequest(User sender, User recipient) {
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
        if (friendshipRepository.existsByRecipientAndStatus(recipient, true)) {
            return friendshipRepository.findAllByRecipientAndStatus(recipient, status);
        } else if (friendshipRepository.existsBySenderAndStatus(recipient, true))
            return friendshipRepository.findAllBySenderAndStatus(recipient, true);
        else
            throw new RequestRejectedException("You have not friends.");
    }

    @Transactional
    public void confirmRequest(User sender, User recipient) {
        if (friendshipRepository.existsBySenderAndRecipientAndStatus(sender, recipient, false)) {
            Friendship friendship = friendshipRepository.findBySenderAndRecipientAndStatus(sender, recipient, false);
            friendship.setStatus(true);
            sender.setFriendsCount(sender.getFriendsCount() + 1);
            recipient.setFriendsCount(recipient.getFriendsCount() + 1);
            friendshipRepository.save(friendship);
            userRepository.save(sender);
            userRepository.save(recipient);
        } else
            throw new RequestRejectedException("You have not received a request from this user.");
    }

    @Transactional
    public void rejectRequest(User sender, User recipient) {
        if (friendshipRepository.existsBySenderAndRecipientAndStatus(sender, recipient, false)) {
            Friendship friendship = friendshipRepository.findBySenderAndRecipientAndStatus(sender, recipient, false);
            friendshipRepository.delete(friendship);
        } else
            throw new RequestRejectedException("You have not received a request from this user.");
    }

    @Transactional
    public void removeFriend(User sender, User recipient) {
        Friendship friendship;
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
        } else
            throw new RequestRejectedException("This user is not your friend.");
    }
}
