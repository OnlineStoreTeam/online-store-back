package com.store.controller;

import com.store.dto.AdminDto;
import com.store.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @version 0.0.1
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AuthorizationAdminController {

    private final AdminService adminService;

    @PostMapping()
    public ResponseEntity<String> adminRegistration(@RequestBody AdminDto adminDto) {
        return new ResponseEntity<>(adminService.adminRegistration(adminDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@RequestBody AdminDto adminDto) {
        return new ResponseEntity<>(adminService.adminLogin(adminDto), HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> adminLogout() {
        return new ResponseEntity<>(adminService.adminLogout(), HttpStatus.OK);
    }
}
