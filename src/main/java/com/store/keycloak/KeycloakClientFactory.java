package com.store.keycloak;


import com.store.webconfig.ApplicationProperties;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.stereotype.Component;

@Component
public class KeycloakClientFactory {

    private final ApplicationProperties applicationProperties;
    private Keycloak keycloakInstance;

    public KeycloakClientFactory(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public synchronized Keycloak getInstance() {
        if (keycloakInstance == null || keycloakInstance.isClosed()) {
            this.keycloakInstance = KeycloakBuilder.builder()
                    .serverUrl(applicationProperties.getKeycloak().getBaseUrl())
                    .realm(applicationProperties.getKeycloak().getRealm())
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .clientId(applicationProperties.getKeycloak().getClientId())
                    .clientSecret(applicationProperties.getKeycloak().getClientSecret()).build();
        }
        return keycloakInstance;
    }
}