package com.store.service.impl;

import com.store.dto.AdminDto;
import com.store.entity.Role;
import com.store.entity.User;
import com.store.repository.UserRepository;
import com.store.service.AdminService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.store.security.Constants.ADMIN_PASSWORD;
import static com.store.security.Constants.ADMIN_USERNAME;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Value(value = "${" + ADMIN_USERNAME + "}")
    private String username;

    @Value(value = "${" + ADMIN_PASSWORD + "}")
    private String password;

    public AdminServiceImpl(UserRepository userRepository,
                            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
//    @PostConstruct
    public String adminRegistration(AdminDto adminDto) {
        log.info("started service:{} setup", AdminServiceImpl.class.getSimpleName());

//        User user = userRepository.getUserByUsername(username);
//        System.out.println("user =++== " + user);

//        if (user == null) {
//            User standartUserChat = User.builder()
//                    .username(username)
//                    .email(username)
//                    .password(passwordEncoder.encode(password))
//                    .roles(Set.of(Role.ADMIN, Role.USER))
//                    .enable(true)
//                    .build();
//            userRepository.save(standartUserChat);
//        }
        return "Registration completed";
    }

    @Override
    public String adminLogin(AdminDto adminDto) {
        if (adminDto.getEmail().equals("admin@admin.com") && adminDto.getPassword().equals("admin")){
            return "Hallow Admin!";
        }
        return "Incorrect username or password";
    }

    @Override
    public String adminLogout() {
        return "Good Bay!";
    }
}
