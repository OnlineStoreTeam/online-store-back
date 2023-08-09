package com.store.security;


import com.store.entity.Role;
import com.store.entity.User;
import com.store.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

import static com.store.security.Constants.ADMIN_PASSWORD;
import static com.store.security.Constants.ADMIN_USERNAME;

@Slf4j
@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Value(value = "${" + ADMIN_USERNAME + "}")
    private String username;

    @Value(value = "${" + ADMIN_PASSWORD + "}")
    private String password;

    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void setup() {
        log.info("started service:{} setup", UserService.class.getSimpleName());

        User userChat = userRepository.getUserByUsername(username);
        System.out.println("userChat = " + userChat);

        if (userChat == null) {
            User standartUserChat = User.builder()
                    .username(username)
                    .email(username)
                    .password(passwordEncoder.encode(password))
                    .roles(Set.of(Role.ADMIN,Role.USER))
                    .enable(true)
                    .build();
            userRepository.save(standartUserChat);

        }
        log.info("service:{} setup finished", UserService.class.getSimpleName());

    }
//    public void updateAuthenticationType(String username, String oauth2ClientName) {
//    	Role authType = Role.valueOf(oauth2ClientName.toUpperCase());
//    	userRepository.updateAuthenticationType(username, authType);
//    	System.out.println("Updated user's authentication type to " + authType);
//    }
}
