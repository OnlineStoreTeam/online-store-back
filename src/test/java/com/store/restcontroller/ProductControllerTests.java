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
@Sql(scripts = "/sqlForTests/productSql/product_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sqlForTests/productSql/product_cleanUp.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProductControllerTests extends BaseWebTest{

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[10].id").exists())
                .andExpect(jsonPath("$.content[11].id").exists())

                .andExpect(status().isOk());
    }

    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(get("/products/11"))
                .andExpect(jsonPath("$.id").value(11L))
                .andExpect(jsonPath("$.name").value("test1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProductByCategory() throws Exception {
        mockMvc.perform(get("/products?categoryId=1"))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[10].id").exists())
                .andExpect(jsonPath("$.content[11].id").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchProducts() throws Exception {
        mockMvc.perform(get("/products/search?name=test1"))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").exists())
                .andExpect(jsonPath("$.content[0].name").value("test1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.ADMIN})
    public void testCreateProduct() throws Exception {
        String requestBody = "{\"article\":\"CCC\",\"name\": \"test\", \"price\": \"100\", \"categoryId\":\"1\"," +
                "\"description\":\"description\", \"quantity\":\"123\", \"productStatus\":\"ACTIVE\"," +
                " \"imagePath\":\"image\"}";

        mockMvc.perform(post("/products").content(requestBody).contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.price").value("100"))
                .andExpect(jsonPath("$.categoryId").value("1"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.quantity").value("123"))
                .andExpect(jsonPath("$.productStatus").value("ACTIVE"))
                .andExpect(jsonPath("$.imagePath").value("image"));
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.ADMIN})
    public void testUpdateProduct() throws Exception {
        String requestBody = "{\"id\":\"11\",\"article\":\"CCC\",\"name\": \"test\", \"price\": \"100\", " +
                "\"categoryId\":\"1\",\"description\":\"description\", \"quantity\":\"123\", \"productStatus\":\"ACTIVE\"," +
                " \"imagePath\":\"image\"}";

        mockMvc.perform(post("/products").content(requestBody).contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.price").value("100"))
                .andExpect(jsonPath("$.categoryId").value("1"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.quantity").value("123"))
                .andExpect(jsonPath("$.productStatus").value("ACTIVE"))
                .andExpect(jsonPath("$.imagePath").value("image"));
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.ADMIN})
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/products/11"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testCreateProduct_WhenUserIsNotAdmin_thenForbidden() throws Exception {
        String requestBody = "{\"article\":\"CCC\",\"name\": \"test\", \"price\": \"100\", \"categoryId\":\"1\"," +
                "\"description\":\"description\", \"quantity\":\"123\", \"productStatus\":\"ACTIVE\"," +
                " \"imagePath\":\"image\"}";

        mockMvc.perform(post("/products").content(requestBody).contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testUpdateProduct_WhenUserIsNotAdmin_thenForbidden() throws Exception {
        String requestBody = "{\"id\":\"11\",\"article\":\"CCC\",\"name\": \"test\", \"price\": \"100\", \"categoryId\":\"1\"," +
                "\"description\":\"description\", \"quantity\":\"123\", \"productStatus\":\"ACTIVE\"," +
                " \"imagePath\":\"image\"}";

        mockMvc.perform(post("/products").content(requestBody).contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testDeleteProduct_WhenUserIsNotAdmin_thenForbidden() throws Exception {
        mockMvc.perform(delete("/products/11"))
                .andExpect(status().isForbidden());
    }
}