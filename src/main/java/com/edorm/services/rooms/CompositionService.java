package com.edorm.services.rooms;

import com.edorm.entities.rooms.Composition;
import com.edorm.models.rooms.AddCompositionRequest;
import com.edorm.repositories.rooms.CompositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompositionService {

    private final CompositionRepository compositionRepository;

    public Composition getComposition(String compositionNumber) {
        return compositionRepository.findByNumber(compositionNumber).orElseThrow(NullPointerException::new);
    }

    public List<Composition> getCompositions() {
        return compositionRepository.findAll();
    }

    public void addComposition(AddCompositionRequest request) {
        Composition composition = new Composition();
        composition.setNumber(request.getNumber());
        compositionRepository.save(composition);
    }

}
