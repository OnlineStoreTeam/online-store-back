package com.store.dto;

import com.store.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class AdminDto {

    private final String userName = "admin";
    private final String password = "admin";
    private final Role role = Role.ADMIN;
}
