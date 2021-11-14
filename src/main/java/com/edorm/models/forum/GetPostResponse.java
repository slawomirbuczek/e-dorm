package com.edorm.models.forum;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostResponse {

    private PostResponse topic;

    private List<PostResponse> responses;

}
