package com.luizalabs.wish_list.application.service;

import com.luizalabs.wish_list.application.dto.WishlistDTO;
import com.luizalabs.wish_list.domain.exception.CustomerNotFoundException;
import com.luizalabs.wish_list.domain.exception.ProductAlreadyExistsException;
import com.luizalabs.wish_list.domain.exception.ProductNotFoundException;
import com.luizalabs.wish_list.domain.model.Customer;
import com.luizalabs.wish_list.domain.model.Product;
import com.luizalabs.wish_list.domain.model.Wishlist;
import com.luizalabs.wish_list.domain.repository.CustomerRepository;
import com.luizalabs.wish_list.domain.repository.ProductRepository;
import com.luizalabs.wish_list.domain.repository.WishlistRepository;
import com.luizalabs.wish_list.infrastructure.api.mapper.WishlistMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WishlistServiceImpTest {

    @InjectMocks
    private WishlistServiceImp wishlistService;

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private WishlistMapper wishlistMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() {
        Customer customer = new Customer("123");
        Product product = new Product("456");
        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(customer);

        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(productRepository.findById("456")).thenReturn(Optional.of(product));
        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));

        wishlistService.addProduct("123", "456");

        verify(wishlistRepository, times(1)).save(wishlist);
    }
    //add product without existed wishlist
    @Test
    public void testAddProductCustomerNotFound() {
        when(customerRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            wishlistService.addProduct("123", "456");
        });

        verify(productRepository, never()).findById(anyString());
        verify(wishlistRepository, never()).findByCustomerId(anyString());
    }

    @Test
    public void testAddProductProductNotFound() {
        Customer customer = new Customer("123");
        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(productRepository.findById("456")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            wishlistService.addProduct("123", "456");
        });

        verify(customerRepository).findById("123");
        verify(productRepository).findById("456");
        verify(wishlistRepository, never()).findByCustomerId(anyString());
    }


    @Test
    public void testAddProductProductAlreadyExists() {
        Customer customer = new Customer("123");
        Product product = new Product("456");
        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(customer);
        wishlist.addProduct(product);

        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(productRepository.findById("456")).thenReturn(Optional.of(product));
        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));

        assertThrows(ProductAlreadyExistsException.class, () -> {
            wishlistService.addProduct("123", "456");
        });
    }
    //test inserir em uma lista que ja tenha 20 produtos
    @Test
    public void testRemoveProduct() {
        Customer customer = new Customer(); // Inicialize conforme necessário
        Wishlist wishlist = new Wishlist(); // Inicialize conforme necessário

        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));

        wishlistService.removeProduct("123", "456");

        verify(wishlistRepository, times(1)).save(wishlist);
    }

    @Test
    public void testGetWishlist() {
        Wishlist wishlist = new Wishlist(); // Inicialize conforme necessário
        WishlistDTO wishlistDTO = new WishlistDTO(); // Inicialize conforme necessário

        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));
        when(wishlistMapper.toDTO(wishlist)).thenReturn(wishlistDTO);

        WishlistDTO result = wishlistService.getWishlist("123");

        assertEquals(wishlistDTO, result);
    }

    @Test
    public void testContainsProduct() {
        Wishlist wishlist = new Wishlist(); // Inicialize conforme necessário
        Product product = new Product(); // Inicialize conforme necessário

        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));
        when(productRepository.findById("456")).thenReturn(Optional.of(product));
        when(wishlist.contains(product)).thenReturn(true);

        boolean result = wishlistService.containsProduct("123", "456");

        assertTrue(result);
    }

    @Test
    public void testContainsProductProductNotFound() {
        when(productRepository.findById("456")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            wishlistService.containsProduct("123", "456");
        });
    }
}
