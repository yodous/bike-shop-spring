package com.example.service.impl;

import com.example.dto.ProductView;
import com.example.mapper.ProductViewMapper;
import com.example.model.Product;
import com.example.model.enums.ProductCategory;
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
        String spaceBefore = " keyboard";
        String spaceAfter = "keyboard ";
        String spaceInside = "key board";

        given(repository.findAllByNameContaining("keyboard")).willReturn(mockedData());

        List<ProductView> products1 = service.getByString(spaceAfter);
        List<ProductView> products2 = service.getByString(spaceBefore);
        List<ProductView> noProducts = service.getByString(spaceInside);

        assertThat(products1).hasSize(3);
        assertThat(products1).containsAll(products2);
        assertThat(noProducts).isEmpty();
    }

    private List<Product> mockedData() {
        return List.of(new Product("mechanical keyboard", "keyboard description", ProductCategory.ELECTRONICS, 99.9),
                new Product("keyboard 60%", "test description", ProductCategory.ELECTRONICS, 12.3),
                new Product("KEYBOARD123", "test description", ProductCategory.ELECTRONICS, 45.67));
    }

    @Test
    void getAllByUsername_ShouldReturnUsersProducts() {
        given(repository.findAllBySellerUsername("test_user123")).willReturn(mockedData());

        List<ProductView> productsSpaceBefore = service.getAllByUsername(" test_user123");
        List<ProductView> productsSpacesBeforeAndAfter = service.getAllByUsername(" test_user123 ");

        assertThat(productsSpaceBefore).isNotEmpty();
        assertThat(productsSpaceBefore).containsAll(productsSpacesBeforeAndAfter);
    }

    @Test
    void getByCategory_WhenSuccess_ThenReturnListOfDTOs() {
        given(mapper.getProductCategory("ELECTRONICS"))
                .willReturn(ProductCategory.ELECTRONICS);
        given(repository.findAllByCategory(ProductCategory.ELECTRONICS))
                .willReturn(mockedData());

        String category = "electronics";
        String categoryWithSpaceAfter = "electronics ";
        String noSuchCategory = " ";

        List<ProductView> products1 = service.getByCategory(category);
        List<ProductView> products2 = service.getByCategory(categoryWithSpaceAfter);
        List<ProductView> noProducts = service.getByCategory(noSuchCategory);

        assertThat(products1).hasSize(3);
        assertThat(products1).containsAll(products2);
        assertThat(noProducts).isEmpty();
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