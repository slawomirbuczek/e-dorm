package com.edorm.services.users;

import com.edorm.entities.users.Resident;
import com.edorm.repositories.users.ResidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResidentService {

    private ResidentRepository residentRepository;

    public Resident getResident(long userId) {
        return residentRepository.findByUserId(userId)
                .orElseThrow(() -> new NullPointerException("Resident not found"));
    }

    public void addResident(Resident resident) {
        residentRepository.save(resident);
    }

}
