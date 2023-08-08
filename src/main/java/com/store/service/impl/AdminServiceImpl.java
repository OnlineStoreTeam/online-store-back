package com.store.service.impl;

import com.store.dto.AdminDto;
import com.store.entity.Role;
import com.store.entity.User;
import com.store.repository.UserRepository;
import com.store.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public String adminRegistration(AdminDto adminDto) {
        User userAdmin = userRepository.save(
                new User()
                        .setEmail(adminDto.getEmail())
                        .setPassword(adminDto.getPassword())
                        .setRole(Role.ADMIN)
                        .setArchive(true));
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
