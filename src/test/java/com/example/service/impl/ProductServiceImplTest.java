package com.example.service.impl;

import com.example.dto.ProductCreateRequest;
import com.example.dto.ProductDTO;
import com.example.exception.ProductNotFoundException;
import com.example.model.Product;
import com.example.model.ProductCategory;
import com.example.dto.ProductUpdate;
import com.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {
    @Mock
    ProductRepository repository;

    @InjectMocks
    ProductServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByString_WhenSuccess_ThenReturnListOfProductsContainingStringInTheirName() {
        when(repository.findAllByNameContaining(anyString())).thenReturn(mockedProductsByName());

        List<ProductDTO> products = service.getByString("fridge");

        assertThat(products).isNotNull();
        assertThat(products).hasSize(2);
    }

    private List<Product> mockedProductsByName() {
        return List.of(
                new Product("fridge x", "description with > 10 characters#0", ProductCategory.HOUSE, 999.99),
                new Product("ffridgee", "description with > 10 characters#0", ProductCategory.HOUSE, 999.99));
    }

    @Test
    void getAll_WhenSuccess_ThenReturnListOfProductDTOs() {
        when(repository.findAll()).thenReturn(mockedData());

        List<ProductDTO> products = service.getAll();

        assertThat(products).isNotNull();
        assertThat(products).hasSize(3);
        assertThat(products.get(1)).isInstanceOf(ProductDTO.class);
    }

    private List<Product> mockedData() {
        return Arrays.asList(
                new Product("test product 0", "description with > 10 characters#0", ProductCategory.ELECTRONICS, 1.11),
                new Product("test product 1", "description with > 10 characters#1", ProductCategory.SPORT, 2.22),
                new Product("test product 2", "description with > 10 characters#2", ProductCategory.HEALTH, 3.33));
    }

    @Test
    void get_WithValidId_ThenReturnProductDTO() {
        Product product = new Product("product dto",
                "description with more than 10 characters", ProductCategory.SPORT, 1.23);
        when(repository.findById(anyInt())).thenReturn(Optional.of(product));

        ProductDTO dto = service.get(1);

        assertThat(dto.getName()).contains("product dto");
    }

    @Test
    void get_WithInvalidId_ThenThrowProductNotFoundException() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class,
                () -> service.get(1));

        assertThat(exception).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void getByCategory_WhenSuccess_ThenReturnListOfDTOs() {
        when(repository.findAllByCategory(any()))
                .thenReturn(productsByCategoriesMock());

        List<ProductDTO> products = service.getByCategory("electronics");

        assertThat(products).isNotNull();
        assertThat(products).hasSize(4);
        assertThat(products.get(0).getCategory()).isEqualTo(ProductCategory.ELECTRONICS.getName());
    }

    private List<Product> productsByCategoriesMock() {
        return Arrays.asList(
                new Product("name0", "description", ProductCategory.ELECTRONICS, 1.23),
                new Product("name1", "description", ProductCategory.ELECTRONICS, 2.34),
                new Product("name2", "description", ProductCategory.ELECTRONICS, 3.45),
                new Product("name3", "description", ProductCategory.ELECTRONICS, 4.56));
    }

    @Test
    void create_WhenSuccess_ThenReturnId() {
        int expectedId = 1;
        Product product = new Product("test_name",
                "description with more then 10 characters",
                ProductCategory.HEALTH, 123.45);
        product.setId(expectedId);
        when(repository.save(any())).thenReturn(product);

        int actualId = service.create(new ProductCreateRequest());

        assertThat(actualId).isEqualTo(expectedId);
    }

    @Test
    void create_WhenFailure_ThenThrowException() {
        Product productWithoutId = new Product();
        when(repository.save(any())).thenReturn(productWithoutId);

        Exception exception = assertThrows(RuntimeException.class,
                () -> service.create(new ProductCreateRequest()));

        String expectedMessage = "Could not save product";
        String actualMessage = exception.getMessage();

        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(actualMessage).contains(expectedMessage);
    }

    @Test
    void update_ExistingResource_ThenReturnId() {
        Product product = new Product();
        when(repository.findById(anyInt())).thenReturn(Optional.of(product));
        product.setId(1);
        when(repository.save(any())).thenReturn(product);

        int updId = service.update(1, new ProductUpdate(
                "new name", "new description", ProductCategory.SPORT, 100));

        assertThat(updId).isNotEqualTo(0);
    }

    @Test
    void update_NonExistingResource_ThenThrowException() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        ProductUpdate productUpdate = new ProductUpdate(
                "new name", "new description", ProductCategory.SPORT, 100);

        Exception exception = assertThrows(RuntimeException.class,
                () -> service.update(1, productUpdate));

        String expectedMessage = "Could not find product";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).contains(expectedMessage);
    }

    @Test
    void updatePrice_WhenSuccess_ThenReturnId() {
        Product product = new Product();
        when(repository.findById(anyInt())).thenReturn(Optional.of(product));
        product.setId(1);
        when(repository.save(any())).thenReturn(product);

        int updId = service.updatePrice(1, 999);

        assertThat(updId).isNotEqualTo(0);
    }

    @Test
    void updatePrice_WhenFailure_ThenThrowException() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> service.updatePrice(1, 100));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Could not find product";

        assertThat(actualMessage).contains(expectedMessage);
    }
}