package com.ncc.mapstruct.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDto {
    private int id;

    private String fullname;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String address;

    private String birthday;

    private String avatar;

}
