package com.example.service.impl;

import com.example.dto.ProductCreateRequest;
import com.example.dto.ProductView;
import com.example.dto.ProductUpdate;
import com.example.exception.ProductNotFoundException;
import com.example.mapper.ProductViewMapper;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

class ProductServiceImplTest {
    @Mock
    private ProductRepository repository;
    @Mock
    private ProductViewMapper mapper;
    @Mock
    private AuthService authService;

    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        given(mapper.mapSourceToView(any())).willReturn(new ProductView());
    }

    @Test
    void getByString_WhenSuccess_ThenReturnListOfProductViews() {
        given(repository.findAllByNameContaining(anyString())).willReturn(mockedData());

        List<ProductView> products = service.getByString("test product");

        assertThat(products).hasSize(3);
    }

    @Test
    void getAll_WhenSuccess_ThenReturnListOfProductDTOs() {
        given(repository.findAll()).willReturn(mockedData());

        List<ProductView> products = service.getAll();

        assertThat(products).hasSize(3);
        assertThat(products.get(1)).isInstanceOf(ProductView.class);
    }

    private List<Product> mockedData() {
        return List.of(new Product(), new Product(), new Product());
    }

    @Test
    void get_WithValidId_ThenReturnProductDTO() {
        given(repository.findById(anyInt())).willReturn(Optional.of(new Product()));

        assertThat(service.get(1)).isInstanceOf(ProductView.class);
    }

    @Test
    void get_WithInvalidId_ThenThrowProductNotFoundException() {
        given(repository.findById(anyInt())).willReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class,
                () -> service.get(1));

        assertThat(exception).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void getByCategory_WhenSuccess_ThenReturnListOfDTOs() {
        given(repository.findAllByCategory(any()))
                .willReturn(mockedData());

        List<ProductView> products = service.getByCategory("electronics");

        assertThat(products).hasSize(3);
    }

    @Test
    void create_WhenSuccess_ThenReturnId() {
        int expectedId = 1;
        Product product = new Product();
        product.setId(expectedId);
        given(repository.save(any())).willReturn(product);

        int actualId = service.create(productCreateRequest);

        assertThat(actualId).isEqualTo(expectedId);
    }

    @Test
    void create_WhenFailure_ThenThrowException() {
        Product productWithoutId = new Product();
        given(repository.save(any())).willReturn(productWithoutId);

        Exception exception = assertThrows(RuntimeException.class,
                () -> service.create(productCreateRequest));

        String expectedMessage = "Could not save product";
        String actualMessage = exception.getMessage();

        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(actualMessage).contains(expectedMessage);
    }

    static final ProductCreateRequest productCreateRequest = new ProductCreateRequest(
            "test name", "test description", "sport", 1.2);

    @Test
    void update_ExistingResource_ThenReturnId() {
        Product product = new Product();
        product.setId(1);

        given(repository.findById(anyInt())).willReturn(Optional.of(product));
        given(repository.save(any())).willReturn(product);

        int updId = service.update(1, new ProductUpdate());

        assertThat(updId).isNotZero();
    }

    @Test
    void update_NonExistingResource_ThenThrowException() {
        given(repository.findById(anyInt())).willReturn(Optional.empty());
        ProductUpdate productUpdate = new ProductUpdate();

        Exception exception = assertThrows(RuntimeException.class,
                () -> service.update(1, productUpdate));

        String expectedMessage = "Could not find product";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).contains(expectedMessage);
    }

    @Test
    void updatePrice_WhenSuccess_ThenReturnId() {
        Product product = new Product();
        product.setId(1);

        given(repository.findById(anyInt())).willReturn(Optional.of(product));
        given(repository.save(any())).willReturn(product);

        int updId = service.updatePrice(1, 999);

        assertThat(updId).isNotZero();
    }

    @Test
    void updatePrice_WhenFailure_ThenThrowException() {
        given(repository.findById(anyInt())).willReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> service.updatePrice(1, 100));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Could not find product";

        assertThat(actualMessage).contains(expectedMessage);
    }
}