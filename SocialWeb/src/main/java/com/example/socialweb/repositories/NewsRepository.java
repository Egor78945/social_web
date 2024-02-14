package com.example.socialweb.repositories;

import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByPublisher(User user);
    News findNewsById(Long id);
}
