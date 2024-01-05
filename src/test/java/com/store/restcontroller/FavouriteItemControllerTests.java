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
@Sql(scripts = "/sqlForTests/favouriteItemSql/favouriteItem_table.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sqlForTests/favouriteItemSql/favouriteItems_cleanUp.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FavouriteItemControllerTests extends BaseWebTest {

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testGetAllFavouriteItems() throws Exception {
        mockMvc.perform(get("/favourites"))
                .andExpect(jsonPath("$.[0].productId").value(1L))
                .andExpect(jsonPath("$.[0].userId").value("testuser"))

                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testAddProductToFavourites() throws Exception {
        mockMvc.perform(post("/favourites?productId=3"))
                .andExpect(jsonPath("$.productId").value(3L))
                .andExpect(jsonPath("$.userId").value("testuser"))

                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = "testuser", authorities = {Role.USER})
    public void testDeleteProductFromFavourites() throws Exception {
        mockMvc.perform(delete("/favourites/1"))
                .andExpect(status().isNoContent());
    }
}