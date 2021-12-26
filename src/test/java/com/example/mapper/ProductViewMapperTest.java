package com.example.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductViewMapperTest {
    @Autowired
    private ProductViewMapper mapper;

//    @Test
//    void mapSourceToView() {
//        Product product = new Product("test product", "description", ProductCategory.SPORT, 1.23);
//        ProductView productView = mapper.mapSourceToView(product);
//
//        assertThat(productView.getName()).isEqualTo(product.getName());
//        assertThat(productView.getCategory()).isEqualToIgnoringCase(product.getCategory().getValue());
//    }
//
//    @Test
//    void mapProductDtoToSource() {
//        ProductRequest productRequest = new ProductRequest("prod","desc","HEALTH", 1.23);
//        User user = new User();
//        user.setUsername("username");
//
//        Product product = mapper.mapProductDtoToSource(productRequest, user);
//
//        assertThat(product.getSeller().getUsername()).isEqualTo("username");
//    }
//
//
//    @Test
//    void getProductCategory_ShouldSucceed() {
//        String validCategoryName = ProductCategory.SPORT.getValue();
//        ProductCategory productCategory = mapper.getProductCategory(validCategoryName);
//
//        assertThat(productCategory).isInstanceOf(ProductCategory.class);
//        assertThat(productCategory.getValue()).isEqualTo(validCategoryName);
//    }
//
//    @Test
//    void getProductCategory_ShouldFailWithException() {
//        assertThrows(IllegalProductCategoryNameException.class,
//                () -> mapper.getProductCategory("invalid_category_name"));
//    }

}