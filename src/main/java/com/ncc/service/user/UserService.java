package com.ncc.service.user;

import com.ncc.mapstruct.dto.token.AccessTokenDto;
import com.ncc.mapstruct.dto.token.RefreshTokenDto;
import com.ncc.mapstruct.dto.user.LoginRequest;
import com.ncc.mapstruct.dto.user.JWTResponse;
import com.ncc.mapstruct.dto.user.UserProfileDto;
import com.ncc.mapstruct.dto.user.UserSignUpDto;
import com.ncc.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;

public interface UserService {

    List<UserProfileDto> getAllUserProfileDto();
    List<User> getAllUsers();
    UserProfileDto getUserById(int id);
    User getUserByEmail(String email);
    User getUserByUsername(String username);

    Page<UserProfileDto> getUsers(int page,int size);
    Page<UserProfileDto> sortByUserNameAsc(int page,int size);

    UserSignUpDto saveUserSignUpDto(UserSignUpDto userSignUpDto);
    UserProfileDto mergeUserProfileDto(UserProfileDto userProfileDto);
    UserSignUpDto signUp(UserSignUpDto userSignUpDto);
    JWTResponse login(LoginRequest credentialsDto, AuthenticationManager authenticationManager);
    void logout();

    AccessTokenDto refreshToken(RefreshTokenDto tokenDto);

    User save(User user);
    User merge(User user);
    User merge(int id,User user);
    void delete(int id);
}
