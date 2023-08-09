package com.store.security;

import com.store.dto.AdminDto;
import com.store.service.AdminService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminRegistrationRunner implements ApplicationRunner {

    private final AdminService adminService;

    public AdminRegistrationRunner(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Call the adminRegistration method here
        AdminDto adminDto = new AdminDto(); // Create an instance of AdminDto as needed
        String result = adminService.adminRegistration(adminDto);
        System.out.println(result);
    }
}
