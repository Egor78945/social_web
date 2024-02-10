package com.example.socialweb.repositories;

import com.example.socialweb.models.entities.Friendship;
import com.example.socialweb.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Boolean existsBySenderAndRecipientAndStatus(User sender, User recipient, Boolean status);
}
