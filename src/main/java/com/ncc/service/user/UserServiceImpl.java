package com.ncc.service.user;

import com.ncc.contants.Constant;
import com.ncc.mapstruct.dto.token.AccessTokenDto;
import com.ncc.mapstruct.dto.token.RefreshTokenDto;
import com.ncc.mapstruct.dto.user.LoginRequest;
import com.ncc.mapstruct.dto.user.JWTResponse;
import com.ncc.mapstruct.dto.user.UserProfileDto;
import com.ncc.mapstruct.dto.user.UserSignUpDto;
import com.ncc.mapstruct.mapper.MapstructMapper;
import com.ncc.model.OrderDetail;
import com.ncc.model.Role;
import com.ncc.model.User;
import com.ncc.model.UserDetailsImpl;
import com.ncc.repository.RoleRepository;
import com.ncc.repository.UserRepository;
import com.ncc.service.jwt.JwtService;
import com.ncc.service.order.OrderService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private MapstructMapper mapstructMapper;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private OrderService orderService;
    private JwtService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           MapstructMapper mapstructMapper,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           OrderService orderService) {
        this.userRepository = userRepository;
        this.mapstructMapper = mapstructMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    @Override
    public List<UserProfileDto> getAllUserProfileDto() {

        return userRepository.findAll().stream()
                .map(mapstructMapper::toUserProfileDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserProfileDto getUserById(int id) {
        return mapstructMapper.toUserProfileDto(userRepository.getById(id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<UserProfileDto> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(mapstructMapper::toUserProfileDto);
    }

    @Override
    public Page<UserProfileDto> sortByUserNameAsc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByOrderByUsernameAsc(pageable).map(mapstructMapper::toUserProfileDto);
    }

    @Override
    public UserSignUpDto saveUserSignUpDto(UserSignUpDto userSignUpDto) {
        User user = mapstructMapper.toEntity(userSignUpDto);
        Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        user.setCreatedAt(date);
        return mapstructMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserProfileDto mergeUserProfileDto(UserProfileDto userProfileDto) {
        User user = mapstructMapper.toEntity(userProfileDto);
        Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        user.setModifiedAt(date);

        return mapstructMapper.toUserProfileDto(merge(user));
    }

    @Transactional
    @Override
    public UserSignUpDto signUp(UserSignUpDto userSignUpDto) {
        User user = userRepository.findByUsernameOrPhoneOrEmail(
                userSignUpDto.getUsername(),
                userSignUpDto.getPhone(),
                userSignUpDto.getEmail()
        );

        if (user == null) {
            user = mapstructMapper.toEntity(userSignUpDto);

            Role role = roleRepository.getById(Constant.ROLE_USER_ID);
            Set<Role> roles = new HashSet<>();
            roles.add(role);

            Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            user.setCreatedAt(date);
            user.setEnabled(true);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(userSignUpDto.getPassword()));
            User userTemp = save(user);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCreatedAt(new Date());
            orderService.save(user.getId());

            return mapstructMapper.toDto(userTemp);
        }
        return mapstructMapper.toDto(user);
    }

    @Override
    public JWTResponse login(LoginRequest credentialsDto, AuthenticationManager authenticationManager) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentialsDto.getUsername(), credentialsDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JWTResponse principle = new JWTResponse(userDetails.getId(), accessToken, refreshToken, roles);
        return principle;
    }

    @Override
    public void logout() {
        /*todo: delete access token and refresh token in redis cache*/
    }

    @Override
    public AccessTokenDto refreshToken(RefreshTokenDto tokenDto) {
        String refreshToken = tokenDto.getRefreshToken();
        String username = null;
        String accessToken = null;

        try {
            username = jwtService.extractUsernameFromToken(refreshToken);
        } catch (ExpiredJwtException e) {
            /*If refresh token has expired --> require re-login*/
            /*In storing refresh token in database case --> create a new refresh token and save to database*/
            System.out.println("JWT Token has expired");
            throw e;
        } catch (IllegalArgumentException e) {
            System.out.println("Disable to extract user from token");
            throw e;
        }

        if (username != null) {
            accessToken = jwtService.generateAccessToken(username);
            /*todo: store access token in redis cache*/
        }

        return new AccessTokenDto(accessToken);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User merge(User user) {
        return save(user);
    }

    @Override
    public User merge(int id, User user) {
        if (this.getUserById(id) != null)
            return userRepository.save(user);
        return null;
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
