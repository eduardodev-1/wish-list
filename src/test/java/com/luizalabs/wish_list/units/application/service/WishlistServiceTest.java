package com.luizalabs.wish_list.units.application.service;

import com.luizalabs.wish_list.application.dto.WishlistDTO;
import com.luizalabs.wish_list.application.service.WishlistService;
import com.luizalabs.wish_list.domain.exception.*;
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

public class WishlistServiceTest {

    @InjectMocks
    private WishlistService wishlistService;

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

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

    @Test
    public void testAddProductWithoutExistingWishlist() {
        Customer customer = new Customer("123");
        Product product = new Product("456");
        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(customer);
        wishlist.addProduct(product);
        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(productRepository.findById("456")).thenReturn(Optional.of(product));
        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.empty());

        wishlistService.addProduct("123", "456");
    }

    @Test
    public void testAddProductCustomerNotFound() {
        when(customerRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> wishlistService.addProduct("123", "456"));

        verify(productRepository, never()).findById(anyString());
        verify(wishlistRepository, never()).findByCustomerId(anyString());
    }

    @Test
    public void testAddProductProductNotFound() {
        Customer customer = new Customer("123");
        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(productRepository.findById("456")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> wishlistService.addProduct("123", "456"));

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

        assertThrows(ProductAlreadyExistsException.class, () -> wishlistService.addProduct("123", "456"));
    }

    @Test
    public void testAddProductWishlistOverflow() {
        Customer customer = new Customer("123");
        Product product = new Product("456");
        Wishlist wishlist = new Wishlist();
        for (int i = 0; i < 20; i++) {
            wishlist.addProduct(new Product(Integer.toString(i)));
        }
        wishlist.setCustomer(customer);

        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(productRepository.findById("456")).thenReturn(Optional.of(product));
        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));

        assertThrows(WishlistOverflowException.class, () -> wishlistService.addProduct("123", "456"));
    }

    @Test
    public void testRemoveProduct() {
        Customer customer = new Customer("123");
        Product product = new Product("1");
        Wishlist wishlist = new Wishlist();
        wishlist.addProduct(product);
        wishlist.setCustomer(customer);

        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));

        wishlistService.removeProduct("123", "1");

        verify(wishlistRepository, times(1)).save(wishlist);
    }

    @Test
    public void testRemoveNonExistingProduct() {
        Customer customer = new Customer("123");
        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(customer);

        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));

        assertThrows(ProductDoesNotExistsException.class, () -> wishlistService.removeProduct("123", "456"));
    }

    @Test
    public void testGetWishlist() {
        Customer customer = new Customer("123");
        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(customer);
        WishlistDTO wishlistDTO = WishlistMapper.INSTANCE.toDTO(wishlist);

        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));

        WishlistDTO result = wishlistService.getWishlist("123");

        assertEquals(wishlistDTO, result);
    }

    @Test
    public void testContainsProduct() {
        Customer customer = new Customer("123");
        Wishlist wishlist = new Wishlist();
        Product product = new Product("456");
        wishlist.setCustomer(customer);
        wishlist.addProduct(product);

        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));
        when(productRepository.findById("456")).thenReturn(Optional.of(product));

        boolean result = wishlistService.containsProduct("123", "456");

        assertTrue(result);
    }

    @Test
    public void testDoesNotContainsProduct() {
        Customer customer = new Customer("123");
        Wishlist wishlist = new Wishlist();
        Product product = new Product("456");
        wishlist.setCustomer(customer);
        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));
        when(productRepository.findById("456")).thenReturn(Optional.of(product));

        boolean result = wishlistService.containsProduct("123", "456");

        assertFalse(result);
    }

    @Test
    public void testContainsProductWishlistNotFound() {
        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.empty());

        assertThrows(WishlistNotFoundException.class, () -> wishlistService.containsProduct("123", "456"));
    }
}
