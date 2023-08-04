package com.store.controller;

import com.store.dto.AdminDto;
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
public class AdminController {

    @PostMapping("/login")
    public ResponseEntity<String> addProduct(@RequestBody AdminDto adminDto) {
        return new ResponseEntity<>("Hallow Admin!", HttpStatus.CREATED);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> getAllProducts() {
        return new ResponseEntity<>("Good Bay!", HttpStatus.OK);
    }
}
