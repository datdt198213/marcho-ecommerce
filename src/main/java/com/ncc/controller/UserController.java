package com.ncc.controller;

import com.ncc.mapstruct.dto.user.UserProfileDto;
import com.ncc.model.UserDetailsImpl;
import com.ncc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserProfileDto>> getUsers(@RequestParam("page")int page, @RequestParam("size")int size) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(userService.getUsers(page, size));
    }

    @GetMapping("/profiles")
    public ResponseEntity<UserProfileDto> getUserProfileDto() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(userService.getUserById(userDetails.getId()));
    }

    @GetMapping("/profiles/{id}")
    public ResponseEntity<UserProfileDto> getUser(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/profiles")
    public ResponseEntity<UserProfileDto> merge(@RequestBody UserProfileDto userProfileDto) {
        return ResponseEntity.ok(userService.mergeUserProfileDto(userProfileDto));
    }
}
