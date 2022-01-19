package com.edorm.controllers.rooms;

import com.edorm.controllers.RestEndpoint;
import com.edorm.entities.rooms.Composition;
import com.edorm.models.rooms.AddCompositionRequest;
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

    @GetMapping("/{compositionNumber}")
    public ResponseEntity<Composition> getComposition(@PathVariable String compositionNumber) {
        return ResponseEntity.ok(compositionService.getComposition(compositionNumber));
    }

    @GetMapping
    public ResponseEntity<List<Composition>> getCompositions() {
        return ResponseEntity.ok(compositionService.getCompositions());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addComposition(@RequestBody AddCompositionRequest request) {
        compositionService.addComposition(request);
    }

}
