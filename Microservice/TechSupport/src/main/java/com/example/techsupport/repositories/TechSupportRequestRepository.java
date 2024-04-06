package com.example.techsupport.repositories;

import com.example.techsupport.models.entities.TechSupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechSupportRequestRepository extends JpaRepository<TechSupportRequest, Long> {

}
