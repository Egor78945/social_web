package com.example.socialweb.services.adminServices;

import com.example.socialweb.enums.UserRole;
import com.example.socialweb.models.entities.BanDetails;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.repositories.BanDetailsRepository;
import com.example.socialweb.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BanDetailsService {
    private final BanDetailsRepository banDetailsRepository;
    private final UserRepository userRepository;

    @Transactional
    public void ban(User banned, User admin, String reason) {
        BanDetails banDetails = new BanDetails.Builder(banned, userRepository.findUserById(admin.getId()))
                .setReason(reason)
                .build();
        banned.setRole(new ArrayList<>());
        userRepository.save(banned);
        banDetailsRepository.save(banDetails);
    }

    @Transactional
    public void unban(User banned) {
        banDetailsRepository.removeBanDetailsByBanned(banned);
        banned.setRole(List.of(UserRole.USER));
        userRepository.save(banned);
    }
}
