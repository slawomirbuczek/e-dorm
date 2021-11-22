package com.edorm.models.messages;

import com.edorm.models.users.GetUserResponse;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMessageResponse {

    private GetUserResponse user;

    private List<MessageResponse> messages;

}
