package com.store.dto.userDTOs;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime registerDate;
    private List<String> roles;
}