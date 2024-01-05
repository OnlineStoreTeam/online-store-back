package com.store.restcontroller;

import com.store.constants.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql(scripts = "/sqlForTests/cartSql/cart_table.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sqlForTests/cartSql/cart_cleanUp.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CartControllerTests extends BaseWebTest {

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testAddItemToCart() throws Exception {
        mockMvc.perform(post("/cart?productId=3"))
                .andExpect(jsonPath("$.productId").value(3L))
                .andExpect(jsonPath("$.userId").value("testuser"))

                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testGetAllCarts() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(jsonPath("$.[0].productId").value(1L))
                .andExpect(jsonPath("$.[0].userId").value("testuser"))

                .andExpect(jsonPath("$.[1].productId").value(2L))
                .andExpect(jsonPath("$.[1].userId").value("testuser"))

                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testUpdateCountOfProduct() throws Exception {
        mockMvc.perform(put("/cart?productId=1&count=2"))
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.userId").value("testuser"))
                .andExpect(jsonPath("$.count").value(2))

                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testDeleteProductFromCart() throws Exception {
        mockMvc.perform(delete("/cart/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testCreateOrderFromCart() throws Exception {
        mockMvc.perform(post("/cart/create-order"))
                .andExpect(jsonPath("$.number").exists())
                .andExpect(jsonPath("$.userId").value("testuser"))
                .andExpect(jsonPath("$.status").value("NEW"))
                .andExpect(jsonPath("$.orderProductDTOList[0].productId").value(1L))
                .andExpect(jsonPath("$.orderProductDTOList[1].productId").value(2L))

                .andExpect(status().isCreated());
    }
}