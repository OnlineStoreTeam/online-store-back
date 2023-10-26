package com.store.service;

import com.store.dto.userDTOs.UserDTO;
import com.store.dto.userDTOs.UserRegisterDTO;
import com.store.keycloak.KeycloakService;
import com.store.mapper.UserMapper;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final KeycloakService keycloakService;
    private final UserMapper userMapper;

    public UserService(KeycloakService keycloakService, UserMapper userMapper) {
        this.keycloakService = keycloakService;
        this.userMapper = userMapper;
    }

    public String getAccessToken() {
        AccessTokenResponse accessTokenResponse = keycloakService.getToken();

        return accessTokenResponse.getToken();
    }

    public UserDTO getUserByUuid(String userUuid) {
        UserRepresentation userRepresentation = keycloakService.getUserByUserUuid(userUuid);

        return userMapper.toDto(userRepresentation);
    }

    public UserDTO createUser(UserRegisterDTO userRegisterDTO) {
        return userMapper.toDto(keycloakService.createUser(userRegisterDTO));
    }
}