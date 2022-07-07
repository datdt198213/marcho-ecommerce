package com.ncc.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncc.mapstruct.dto.user.JWTResponse;
import com.ncc.mapstruct.dto.user.LoginRequest;
import com.ncc.model.UserDetailsImpl;
import com.ncc.service.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private JwtService jwtService;
    private ObjectMapper objectMapper;
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(JwtService jwtService,
                                   AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        objectMapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    ));

            return authentication;
        } catch (IOException e) {
            throw new BadCredentialsException("unable to attempt authentication with details provided");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        /*todo: store token in redis cache*/

        List<String> roles = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JWTResponse jwtResponse = new JWTResponse(userDetails.getId(), accessToken, refreshToken, roles);

        response.setContentType(APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getOutputStream(), jwtResponse);
    }
}
