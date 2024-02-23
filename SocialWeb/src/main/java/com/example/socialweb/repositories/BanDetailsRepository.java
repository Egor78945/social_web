package com.example.socialweb.repositories;

import com.example.socialweb.models.entities.BanDetails;
import com.example.socialweb.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanDetailsRepository extends JpaRepository<BanDetails, Long> {
    Boolean existsBanDetailsByBanned(User banned);
    void removeBanDetailsByBanned(User banned);
}
