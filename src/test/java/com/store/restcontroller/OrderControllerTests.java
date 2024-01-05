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
@Sql(scripts = "/sqlForTests/orderSql/order_table.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sqlForTests/orderSql/orderProduct_table.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sqlForTests/orderSql/orderProduct_cleanUp.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/sqlForTests/orderSql/order_cleanUp.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class OrderControllerTests extends BaseWebTest {
    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testGetOrderByNumber() throws Exception {
        mockMvc.perform(get("/order/number"))
                .andExpect(jsonPath("$.number").value("number"))
                .andExpect(jsonPath("$.status").value("NEW"))
                .andExpect(jsonPath("$.orderProductDTOList").exists())

                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testGetAllOrdersByUserId() throws Exception {
        mockMvc.perform(get("/orders")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())

                .andExpect(jsonPath("$.content[0].userId").value("testuser"))
                .andExpect(jsonPath("$.content[0].number").exists())
                .andExpect(jsonPath("$.content[0].status").value("NEW"))
                .andExpect(jsonPath("$.content[0].price").exists())
                .andExpect(jsonPath("$.content[0].count").value(1))
                .andExpect(jsonPath("$.content[0].orderProductDTOList").isArray());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.ADMIN})
    public void testGetAllOrders() throws Exception {
        mockMvc.perform(get("/orders/all")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())

                .andExpect(jsonPath("$.content[0].userId").value("testuser"))
                .andExpect(jsonPath("$.content[0].number").exists())
                .andExpect(jsonPath("$.content[0].status").value("NEW"))
                .andExpect(jsonPath("$.content[0].price").exists())
                .andExpect(jsonPath("$.content[0].count").value(1))
                .andExpect(jsonPath("$.content[0].orderProductDTOList").isArray());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testGetAllOrder_WhenUserIsNotAdmin_thenForbidden() throws Exception {
        mockMvc.perform(get("/orders/all")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testCancelOrder() throws Exception {
        mockMvc.perform(delete("/order/number"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testConfirmOrder() throws Exception {
        mockMvc.perform(put("/order/confirm/number"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.ADMIN})
    public void testExportAllOrders() throws Exception {
        mockMvc.perform(get("/orders/export"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testExportAllOrder_WhenUserIsNotAdmin_thenForbidden() throws Exception {
        mockMvc.perform(get("/orders/export"))
                .andExpect(status().isForbidden());
    }
}