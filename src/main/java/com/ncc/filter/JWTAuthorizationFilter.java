package com.ncc.filter;

import com.ncc.service.jwt.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ncc.security.SecurityConfig.PUBLIC_ENDPOINTS;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private UserDetailsService userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JWTAuthorizationFilter(JwtService jwtService,
                                  UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String accessToken = null;

        if (isPassFilter(request.getServletPath())) {
            filterChain.doFilter(request, response);

            return;
        }

        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            accessToken = tokenHeader.substring(7);

            /*todo: if token has stored in cache --> pass*/

            try {
                username = jwtService.extractUsernameFromToken(accessToken);
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has expired");
                throw e;
            } catch (IllegalArgumentException e) {
                logger.error("Disable to extract user from token");
                throw e;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.validateJwtToken(accessToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isPassFilter(String contextPath) {
        String[] paths = contextPath.split("/");

        for (int i = 1; i < paths.length; i++) {
            if (PUBLIC_ENDPOINTS.contains(paths[i]))
                return true;
        }

        return false;
    }
}
