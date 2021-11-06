package com.edorm.services.users;

import com.edorm.entities.users.Resident;
import com.edorm.repositories.users.ResidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResidentService {

    private ResidentRepository residentRepository;

    public void addResident(Resident resident) {
        residentRepository.save(resident);
    }

}
