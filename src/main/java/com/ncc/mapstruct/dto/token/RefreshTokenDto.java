package com.ncc.mapstruct.dto.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {

    @JsonProperty("x-refresh-token")
    private String refreshToken;
}
