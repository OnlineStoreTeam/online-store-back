package com.store.service;

import com.store.constants.Role;
import com.store.dto.userDTOs.UserDTO;
import com.store.dto.userDTOs.UserRegisterDTO;
import com.store.exception.InvalidDataException;
import com.store.keycloak.KeycloakService;
import com.store.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    @Mock
    private KeycloakService keycloakService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetAccessToken() {
        AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
        accessTokenResponse.setToken("some token");

        when(keycloakService.getToken()).thenReturn(accessTokenResponse);

        String result = userService.getAccessToken();

        assertEquals(accessTokenResponse.getToken(), result);
    }

    @Test
    public void testGetUserByUuid() {
        String userUuid = "uuid";

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(userUuid);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userUuid);

        when(keycloakService.getUserByUserUuid(any(String.class))).thenReturn(userRepresentation);
        when(userMapper.toDto(any(UserRepresentation.class))).thenReturn(userDTO);

        UserDTO result = userService.getUserByUuid(userUuid);

        verify(keycloakService).getUserByUserUuid(any(String.class));
        verify(userMapper).toDto(any(UserRepresentation.class));

        assertEquals(userDTO.getUserId(), result.getUserId());
    }

    @Test
    public void testCreateUser() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail("email");
        userRegisterDTO.setPassword("superpass");
        userRegisterDTO.setConfirmPassword("superpass");
        userRegisterDTO.setFirstName("First Name");
        userRegisterDTO.setLastName("Last name");

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId("userUuid");
        userRepresentation.setUsername("Test");
        userRepresentation.setEmail("email");
        userRepresentation.setFirstName("First Name");
        userRepresentation.setLastName("Last Name");
        userRepresentation.setRealmRoles(List.of(Role.USER));

        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setUserId("userUuid");
        expectedUserDTO.setEmail("email");
        expectedUserDTO.setFirstName("First Name");
        expectedUserDTO.setLastName("Last Name");
        expectedUserDTO.setRegisterDate(LocalDateTime.now());
        expectedUserDTO.setRoles(List.of(Role.USER));

        when(keycloakService.createUser(any(UserRegisterDTO.class))).thenReturn(userRepresentation);
        when(userMapper.toDto(any(UserRepresentation.class))).thenReturn(expectedUserDTO);

        UserDTO result = userService.createUser(userRegisterDTO);

        verify(keycloakService).createUser(any(UserRegisterDTO.class));
        verify(userMapper).toDto(any(UserRepresentation.class));

        assertEquals(expectedUserDTO.getUserId(), result.getUserId());
        assertEquals(expectedUserDTO.getEmail(), result.getEmail());
        assertEquals(expectedUserDTO.getFirstName(), result.getFirstName());
        assertEquals(expectedUserDTO.getLastName(), result.getLastName());
        assertEquals(expectedUserDTO.getRegisterDate(), result.getRegisterDate());
        assertEquals(expectedUserDTO.getRoles(), result.getRoles());
    }

    @Test
    public void testCreateUser_WhenPasswordsDoesNotMatch_ShouldThrowException() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail("email");
        userRegisterDTO.setPassword("superpass");
        userRegisterDTO.setConfirmPassword("notsuperpass");
        userRegisterDTO.setFirstName("First Name");
        userRegisterDTO.setLastName("Last name");

        when(keycloakService.createUser(userRegisterDTO)).thenThrow(InvalidDataException.class);

        assertThrows(InvalidDataException.class, () -> userService.createUser(userRegisterDTO));

        verify(keycloakService).createUser(userRegisterDTO);
    }
}