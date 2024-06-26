package com.example.socialweb.services.adminServices;

import com.example.socialweb.enums.UserRole;
import com.example.socialweb.exceptions.RequestCancelledException;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.BanModel;
import com.example.socialweb.repositories.NewsRepository;
import com.example.socialweb.repositories.UserRepository;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final BanDetailsService banDetailsService;

    @Transactional
    public void banUser(BanModel banModel, User admin) throws RequestCancelledException {
        User banned = userRepository.findUserById(banModel.getUserId());
        if (!banned.isBan()) {
            if (!banned.getRole().contains(UserRole.ADMIN))
                banDetailsService.ban(banned, admin, banModel.getReason());
            else
                throw new RequestCancelledException("Admin can not be banned.");
        } else
            throw new RequestCancelledException("The user is already banned.");
    }

    @Transactional
    public void unbanUser(Long userId) throws RequestCancelledException {
        User banned = userRepository.findUserById(userId);
        if (banned.isBan()) {
            banDetailsService.unban(banned);
        } else
            throw new RequestCancelledException("The user is not banned.");
    }
}
