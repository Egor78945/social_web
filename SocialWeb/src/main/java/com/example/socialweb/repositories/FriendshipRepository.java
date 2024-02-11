package com.example.socialweb.repositories;

import com.example.socialweb.models.entities.Friendship;
import com.example.socialweb.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Boolean existsBySenderAndRecipientAndStatus(User sender, User recipient, Boolean status);
    List<Friendship> findAllByRecipientAndStatus(User recipient, Boolean status);
    Friendship findBySenderAndRecipientAndStatus(User sender, User recipient, Boolean status);
    Boolean existsByRecipientAndStatus(User recipient, Boolean status);
    Boolean existsBySenderAndStatus(User sender, Boolean status);
    List<Friendship> findAllBySenderAndStatus(User sender, Boolean status);
}
