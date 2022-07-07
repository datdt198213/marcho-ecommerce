package com.ncc.controller;

import com.ncc.mapstruct.dto.token.AccessTokenDto;
import com.ncc.mapstruct.dto.token.RefreshTokenDto;
import com.ncc.mapstruct.dto.user.UserSignUpDto;
import com.ncc.service.jwt.JwtService;
import com.ncc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Autowired
    public LoginController(JwtService jwtService, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpDto> signup(@RequestBody UserSignUpDto userSignUpDto) {
        UserSignUpDto dto = userService.signUp(userSignUpDto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("X-Account-Id") String accountId) {
        userService.logout();

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<AccessTokenDto> refreshToken(@RequestBody RefreshTokenDto tokenDto){
        AccessTokenDto token = userService.refreshToken(tokenDto);

        return ResponseEntity.ok(token);
    }
}
