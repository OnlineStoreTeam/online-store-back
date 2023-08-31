package com.store.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class Constants {
    //============================== PROPERTY KEYS =======================
    public static final String ADMIN_USERNAME = "gcp.vars.admin.user";
    public static final String ADMIN_PASSWORD = "gcp.vars.admin.password";
}
