package com.ncc.mapstruct.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTResponse {
    private int userId;

    @JsonProperty("x-access-token")
    private String accessToken;

    @JsonProperty("x-refresh-token")
    private String refreshToken;

    private List<String> roles;
}

