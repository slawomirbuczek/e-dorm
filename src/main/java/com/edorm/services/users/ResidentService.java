package com.edorm.services.users;

import com.edorm.entities.rooms.Room;
import com.edorm.entities.users.Resident;
import com.edorm.entities.users.User;
import com.edorm.models.users.AddResidentRequest;
import com.edorm.models.users.GetResidentInfoResponse;
import com.edorm.models.users.GetResidentResponse;
import com.edorm.repositories.users.ResidentRepository;
import com.edorm.services.images.ImageUtil;
import com.edorm.services.rooms.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResidentService {

    private ResidentRepository residentRepository;
    private UserService userService;
    private RoomService roomService;

    public GetResidentInfoResponse getResidentInfo(long userId) {
        return mapResidentToInfoResponse(getResident(userId));
    }

    public List<GetResidentResponse> getResidents() {
        return residentRepository.findAll().stream().map(this::mapResidentToResponse).collect(Collectors.toList());
    }

    public void addResident(AddResidentRequest request, long userId) {
        final User user = userService.getUser(userId);
        final Room room = roomService.getRoom(request.getRoomNumber());

        Resident resident = new Resident();
        resident.setUser(user);
        resident.setRoom(room);
        residentRepository.save(resident);
    }

    private Resident getResident(long userId) {
        return residentRepository.findByUserId(userId).orElseThrow(() -> new NullPointerException("Resident not found"));
    }

    private GetResidentInfoResponse mapResidentToInfoResponse(Resident resident) {
        final byte[] photo = ImageUtil.getImageContent(resident.getUser().getPhoto());

        GetResidentInfoResponse response = new GetResidentInfoResponse();
        response.setFirstName(resident.getUser().getFirstName());
        response.setLastName(resident.getUser().getLastName());
        response.setPhoneNumber(resident.getUser().getPhoneNumber());
        response.setBirthday(resident.getUser().getBirthday());
        response.setPhoto(photo);
        response.setRoomNumber(resident.getRoom().getNumber());
        response.setCompositionNumber(resident.getRoom().getComposition().getNumber());
        return response;
    }

    private GetResidentResponse mapResidentToResponse(Resident resident) {
        GetResidentResponse response = new GetResidentResponse();
        response.setUserId(resident.getUser().getId());
        response.setFullName(resident.getUser().getFirstName() + " " + resident.getUser().getLastName());
        return response;
    }

}
