package com.ncc.mapstruct.dto.feedback;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDto {
    private int id;

    private String message;

    private int vote;

    private String email;

    private String username;

    private String avatar;

    private String prodId;
}
