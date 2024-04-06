package com.example.socialweb.repositories;

import com.example.socialweb.models.entities.Message;
import com.example.socialweb.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByRecipient(User recipient);
    List<Message> findAllBySenderAndRecipient(User sender, User recipient);
}
