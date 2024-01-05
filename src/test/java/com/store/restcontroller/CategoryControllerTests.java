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
@Sql(scripts = "/sqlForTests/categorySql/category_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sqlForTests/categorySql/category_cleanUp.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CategoryControllerTests extends BaseWebTest {

    @Test
    public void testGetAllCategories() throws Exception {
        mockMvc.perform(get("/category"))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[6].id").exists())
                .andExpect(jsonPath("$.content[7].id").exists())

                .andExpect(status().isOk());
    }

    @Test
    public void testGetCategoryById() throws Exception {
        mockMvc.perform(get("/category/7"))
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.name").value("name1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.ADMIN})
    public void testCreateCategory() throws Exception {
        String requestBody = "{\"name\": \"category\", \"title\": \"title\", \"path\":\"path\"}";

        mockMvc.perform(post("/category")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.ADMIN})
    public void testUpdateCategory() throws Exception {

        String requestBody = "{\"id\": \"7\",\"name\": \"category1\", \"title\": \"title3\", \"path\":\"path3\"}";

        mockMvc.perform(put("/category").content(requestBody).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("category1"));
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.ADMIN})
    public void testDeleteCategory() throws Exception {
        mockMvc.perform(delete("/category/7"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testCreateCategory_WhenUserIsNotAdmin_thenForbidden() throws Exception {
        String requestBody = "{\"name\": \"category\", \"title\": \"title\", \"path\":\"path\"}";

        mockMvc.perform(post("/category")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testUpdateCategory_WhenUserIsNotAdmin_thenForbidden() throws Exception {
        String requestBody = "{\"id\": \"7\",\"name\": \"category1\", \"title\": \"title3\", \"path\":\"path3\"}";

        mockMvc.perform(put("/category")
                        .content(requestBody)
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testDeleteCategory_WhenUserIsNotAdmin_thenForbidden() throws Exception {
        mockMvc.perform(delete("/category/7"))
                .andExpect(status().isForbidden());
    }
}