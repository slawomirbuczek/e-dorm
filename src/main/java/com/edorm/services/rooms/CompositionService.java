package com.edorm.services.rooms;

import com.edorm.entities.rooms.Composition;
import com.edorm.repositories.rooms.CompositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompositionService {

    private final CompositionRepository compositionRepository;

    public void addComposition(Composition composition) {
        compositionRepository.save(composition);
    }

    public Composition getComposition(String name) {
        return compositionRepository.findByName(name)
                .orElseThrow(NullPointerException::new);
    }

    public List<Composition> getCompositions() {
        return compositionRepository.findAll();
    }

    public void deleteComposition(String name) {
        compositionRepository.deleteByName(name);
    }

}
