package com.example.mapper;

import com.example.dto.ProductRequest;
import com.example.dto.ProductView;
import com.example.exception.IllegalProductCategoryNameException;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.enums.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductViewMapper {
    @Mapping(target = "lastModified", expression = "java(String.valueOf(product.getModifiedAt()))")
    ProductView mapSourceToView(Product product);


    @Mapping(target = "category", expression = "java(getProductCategory(request.getCategory()))")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "modifiedAt", expression = "java(java.time.Instant.now())")
    Product mapProductDtoToSource(ProductRequest request, User user);

    default ProductCategory getProductCategory(String categoryName) {
        try {
            return ProductCategory.valueOf(categoryName.toUpperCase());
        } catch (NullPointerException e) {
            throw new RuntimeException("product category is null");
        }
        catch (IllegalArgumentException e) {
            throw new IllegalProductCategoryNameException();
        }
    }

}
