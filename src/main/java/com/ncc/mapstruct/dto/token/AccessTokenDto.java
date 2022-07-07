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
public class AccessTokenDto {
    @JsonProperty("x-access-token")
    private String accessToken;
}
