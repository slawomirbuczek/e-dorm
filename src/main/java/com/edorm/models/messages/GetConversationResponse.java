package com.edorm.models.messages;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetConversationResponse {

    private long conversationId;

    private String fullName;

    private byte[] photo;

}
