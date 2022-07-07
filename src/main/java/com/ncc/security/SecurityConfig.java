package com.ncc.security;

import com.ncc.filter.JWTAuthenticationFilter;
import com.ncc.filter.JWTAuthorizationFilter;
import com.ncc.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    public static final List<String> PUBLIC_ENDPOINTS = Arrays.asList("login", "signup","refresh/token");

    @Autowired
    public SecurityConfig(JwtService jwtService,
                          PasswordEncoder passwordEncoder,
                          UserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(jwtService, authenticationManagerBean());
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.cors().and().csrf().disable();

        http.authorizeRequests()
                .antMatchers("/api/users").hasRole("ADMIN");

        http.authorizeRequests()
                .antMatchers("/api/profiles/**").hasAnyRole("ADMIN", "USER");

        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/api/products/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/products/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyRole("ADMIN");

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/categories/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/categories/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/categories/**").hasAnyRole("ADMIN");

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/colors/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/colors/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/colors/**").hasAnyRole("ADMIN");

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/discounts/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/discounts/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/discounts/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/discounts/**").hasAnyRole("ADMIN");

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/sizes/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/sizes/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/sizes/**").hasAnyRole("ADMIN");

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/feedbacks/**").hasAnyRole("USER");

        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/api/orders/**").hasAnyRole("USER");

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/products/**",
                        "/api/categories/**",
                        "/api/sizes/**",
                        "/api/feedbacks/**",
                        "/api/colors/**").permitAll()
                .antMatchers("/api/login", "/api/signup","/api/refresh/token").permitAll();

        http.authorizeRequests().anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilter(jwtAuthenticationFilter);

        http.addFilterBefore(new JWTAuthorizationFilter(jwtService, userDetailsService),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);

        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
