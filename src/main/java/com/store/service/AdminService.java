package com.store.service;

import com.store.dto.AdminDto;

public interface AdminService {

    String adminRegistration(AdminDto adminDto);
    String adminLogin(AdminDto adminDto);
    String adminLogout();
}
