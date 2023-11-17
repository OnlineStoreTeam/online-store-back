package com.store.keycloak;

import com.store.constants.Role;
import com.store.dto.userDTOs.UserRegisterDTO;
import com.store.exception.ConflictException;
import com.store.exception.InvalidDataException;
import com.store.webconfig.ApplicationProperties;
import org.apache.http.impl.client.HttpClientBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.store.keycloak.CredentialsUtils.createPasswordCredentials;

@Component
public class KeycloakService {
    private static final Logger logger = LoggerFactory.getLogger(KeycloakService.class);
    private final ApplicationProperties applicationProperties;
    private final KeycloakClientFactory keycloakClientFactory;

    public KeycloakService(ApplicationProperties applicationProperties, KeycloakClientFactory keycloakClientFactory) {
        this.applicationProperties = applicationProperties;
        this.keycloakClientFactory = keycloakClientFactory;
    }

    public AccessTokenResponse getToken() {
        Configuration authzConf = new Configuration(this.applicationProperties.getKeycloak().getBaseUrl(),
                this.applicationProperties.getKeycloak().getRealm(), this.applicationProperties.getKeycloak().getClientId(),
                Collections.singletonMap("secret", this.applicationProperties.getKeycloak().getClientSecret()),
                HttpClientBuilder.create().build());
        AuthzClient authzClient = AuthzClient.create(authzConf);
        return authzClient.obtainAccessToken();
    }

    private RealmResource realmResource() {
        return keycloakClientFactory.getInstance()
                .realm(applicationProperties.getKeycloak().getRealm());
    }

    private UsersResource usersResource() {
        return keycloakClientFactory.getInstance()
                .realm(applicationProperties.getKeycloak().getRealm())
                .users();
    }

    public List<String> getUserRoles(String userUuid) {
        return usersResource()
                .get(userUuid)
                .roles()
                .realmLevel()
                .listAll()
                .stream()
                .map(RoleRepresentation::getName)
                .toList();
    }

    private void addRolesToUser(List<RoleRepresentation> realmRoles, String userUuid) {
        usersResource().get(userUuid).roles().realmLevel().add(realmRoles);
    }

    private RoleRepresentation getRole(String role) {
        return realmResource().roles().get(role).toRepresentation();
    }

    public UserRepresentation getUserByUserUuid(String userUuid) {
        UserRepresentation userRepresentation = usersResource().get(userUuid).toRepresentation();
        userRepresentation.setRealmRoles(getUserRoles(userUuid));
        return userRepresentation;
    }

    public UserRepresentation createUser(UserRegisterDTO userRegisterDTO) {
        if (!Objects.equals(userRegisterDTO.getPassword(), userRegisterDTO.getConfirmPassword())) {
            throw new InvalidDataException("Password and Confirm Password does not match");
        }

        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setUsername(userRegisterDTO.getUsername());
        userRepresentation.setEmail(userRegisterDTO.getEmail());
        userRepresentation.setFirstName(userRegisterDTO.getFirstName());
        userRepresentation.setLastName(userRegisterDTO.getLastName());
        userRepresentation.setCredentials(List.of(createPasswordCredentials(userRegisterDTO.getPassword())));
        userRepresentation.setEnabled(true);

        if (!ObjectUtils.isEmpty(userRegisterDTO.getEmail())) {
            userRepresentation.setEmailVerified(true);
        }

        Response response = usersResource().create(userRepresentation);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());

        switch (httpStatus) {
            case CREATED -> logger.info("User {} successfully created", userRegisterDTO);
            case CONFLICT -> throw new ConflictException("User " + userRegisterDTO + " already exists");
            case BAD_REQUEST -> throw new InvalidDataException("Cannot create user " + userRegisterDTO);
            default ->
                    throw new ResponseStatusException(httpStatus, "Problems occurred while creating user " + userRegisterDTO);
        }

//        String path = response.getLocation().getPath();
//        String userUuid = path.substring(path.lastIndexOf('/') + 1);

        UserRepresentation users = usersResource().search(userRegisterDTO.getUsername(), true).get(0);
        userRepresentation.setId(users.getId());
        userRepresentation.setCreatedTimestamp(users.getCreatedTimestamp());
        addRolesToUser(List.of(getRole(Role.USER)), users.getId());
        userRepresentation.setRealmRoles(getUserRoles(users.getId()));

        return userRepresentation;
    }
}