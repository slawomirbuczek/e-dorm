package com.edorm.controllers.users;

import com.edorm.controllers.RestEndpoint;
import com.edorm.models.users.AddResidentRequest;
import com.edorm.models.users.GetResidentInfoResponse;
import com.edorm.models.users.GetResidentResponse;
import com.edorm.services.users.ResidentService;
import com.edorm.utils.PrincipalUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(RestEndpoint.RESIDENT)
@AllArgsConstructor
public class ResidentController {

    private final ResidentService residentService;

    @GetMapping("/{userId}")
    public ResponseEntity<GetResidentInfoResponse> getResidentInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(residentService.getResidentInfo(userId));
    }

    @GetMapping
    public ResponseEntity<List<GetResidentResponse>> getResidents() {
        return ResponseEntity.ok(residentService.getResidents());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addResident(@RequestBody AddResidentRequest request, Principal principal) {
        residentService.addResident(request, PrincipalUtil.getUserId(principal));
    }

}
