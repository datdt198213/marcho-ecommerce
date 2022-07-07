package com.ncc.mapstruct.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDto {
    private String username;

    private String password;

    private String email;

    private String phone;
}

