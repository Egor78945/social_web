package com.example.techsupport.services;

import com.example.techsupport.models.entities.TechSupportRequest;
import com.example.techsupport.repositories.TechSupportRequestRepository;
import com.example.techsupport.services.kafka.consumers.TechSupportConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TechSupportService {
    private final TechSupportRequestRepository techSupportRequestRepository;

    public void save(TechSupportRequest techSupportRequest){
        techSupportRequestRepository.save(techSupportRequest);
    }

}
