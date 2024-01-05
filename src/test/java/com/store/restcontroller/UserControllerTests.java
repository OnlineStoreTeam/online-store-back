package com.store.restcontroller;

import com.store.constants.Role;
import com.store.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTests extends BaseWebTest{

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.ADMIN})
    public void testGetUser() throws Exception {
        String userUuid = "ba76035f-5f53-450a-9e55-1c3f8c6d4865"; //userUuid of test-user

        mockMvc.perform(get("/users/" + userUuid)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void whenGetUserWithRoleUser_thenForbidden() throws Exception {
        String userUuid = "ba76035f-5f53-450a-9e55-1c3f8c6d4865"; //userUuid of test-user

        mockMvc.perform(get("/users/" + userUuid)).andExpect(status().isForbidden());
    }
}