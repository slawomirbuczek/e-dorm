package com.edorm.controllers.rooms;

import com.edorm.controllers.RestEndpoint;
import com.edorm.entities.rooms.Composition;
import com.edorm.services.rooms.CompositionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestEndpoint.COMPOSITION)
@AllArgsConstructor
public class CompositionController {

    private final CompositionService compositionService;

    @GetMapping("/{name}")
    public ResponseEntity<Composition> getComposition(@PathVariable String name) {
        return ResponseEntity.ok(compositionService.getComposition(name));
    }

    @GetMapping
    public ResponseEntity<List<Composition>> getCompositions() {
        return ResponseEntity.ok(compositionService.getCompositions());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addComposition(@RequestBody Composition composition) {
        compositionService.addComposition(composition);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComposition(@PathVariable String name) {
        compositionService.deleteComposition(name);
    }

}
