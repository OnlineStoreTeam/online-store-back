package com.store.restcontroller;


import com.store.constants.Role;
import com.store.dto.userDTOs.UserDTO;
import com.store.dto.userDTOs.UserRegisterDTO;
import com.store.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    @GetMapping("/users/{userUuid}")
    public UserDTO getUserByUserUuid(@PathVariable String userUuid) {
        return userService.getUserByUuid(userUuid);
    }

    @PostMapping("/users/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.createUser(userRegisterDTO);
    }
}