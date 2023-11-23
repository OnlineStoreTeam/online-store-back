package com.store;

import com.store.webconfig.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class OnlineStoreBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineStoreBackApplication.class, args);
    }
}