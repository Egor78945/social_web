package com.example.socialweb.services;

import com.example.socialweb.models.entities.Friendship;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.repositories.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;

    public boolean containsBySenderAndRecipientAndStatus(User sender, User recipient, Boolean status) {
        return friendshipRepository.existsBySenderAndRecipientAndStatus(sender, recipient, status);
    }

    @Transactional
    public void friendRequest(User sender, User recipient) {
        if (containsBySenderAndRecipientAndStatus(sender, recipient, false))
            throw new RequestRejectedException("You already sent request to this user.");
        else if (containsBySenderAndRecipientAndStatus(sender, recipient, true))
            throw new RequestRejectedException("This user is already your friend.");
        else {
            Friendship friendship = new Friendship(sender, recipient);
            friendshipRepository.save(friendship);
        }
    }
}
