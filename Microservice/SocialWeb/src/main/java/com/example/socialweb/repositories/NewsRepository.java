package com.example.socialweb.repositories;

import com.example.socialweb.models.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT n FROM News n where n.publisher.id=?1")
    List<News> findAllByPublisherId(Long id);
    News findNewsById(Long id);
}
