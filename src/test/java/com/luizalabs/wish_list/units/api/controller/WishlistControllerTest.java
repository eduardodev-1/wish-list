package com.luizalabs.wish_list.units.api.controller;

import com.luizalabs.wish_list.application.dto.WishlistDTO;
import com.luizalabs.wish_list.application.service.WishlistService;
import com.luizalabs.wish_list.domain.exception.CustomerNotFoundException;
import com.luizalabs.wish_list.infrastructure.api.controller.WishlistController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(WishlistController.class)
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @Test
    void testAddProductSuccess() throws Exception {
        Mockito.doNothing().when(wishlistService).addProduct(anyString(), anyString());

        mockMvc.perform(post("/api/wishlist/{customerId}/add", "123")
                        .param("productId", "456"))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto adicionado com sucesso!"));
    }

    @Test
    void testAddProductException() throws Exception {
        Mockito.doThrow(new CustomerNotFoundException("123")).when(wishlistService).addProduct(anyString(), anyString());

        mockMvc.perform(post("/api/wishlist/{customerId}/add", "123")
                        .param("productId", "456"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testRemoveProductSuccess() throws Exception {
        Mockito.doNothing().when(wishlistService).removeProduct(anyString(), anyString());

        mockMvc.perform(delete("/api/wishlist/{customerId}/remove", "123")
                        .param("productId", "456"))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto removido com sucesso!"));
    }

    @Test
    void testRemoveProductException() throws Exception {
        Mockito.doThrow(new CustomerNotFoundException("123")).when(wishlistService).removeProduct(anyString(), anyString());

        mockMvc.perform(delete("/api/wishlist/{customerId}/remove", "123")
                        .param("productId", "456"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetWishlistSuccess() throws Exception {
        WishlistDTO wishlistDTO = new WishlistDTO();  // Mock the DTO as needed
        Mockito.when(wishlistService.getWishlist(anyString())).thenReturn(wishlistDTO);

        mockMvc.perform(get("/api/wishlist/{customerId}", "123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetWishlistException() throws Exception {
        Mockito.doThrow(new CustomerNotFoundException("123")).when(wishlistService).getWishlist(anyString());

        mockMvc.perform(get("/api/wishlist/{customerId}", "123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testContainsProductSuccess() throws Exception {
        Mockito.when(wishlistService.containsProduct(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(get("/api/wishlist/{customerId}/contains", "123")
                        .param("productId", "456"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testContainsProductException() throws Exception {
        Mockito.doThrow(new CustomerNotFoundException("123")).when(wishlistService).containsProduct(anyString(), anyString());

        mockMvc.perform(get("/api/wishlist/{customerId}/contains", "123")
                        .param("productId", "456"))
                .andExpect(status().isNotFound());
    }
}
