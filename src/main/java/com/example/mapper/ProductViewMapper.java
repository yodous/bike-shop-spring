package com.example.mapper;

import com.example.dto.ProductView;
import com.example.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductViewMapper {
    @Mappings(
            @Mapping(target = "lastModified", source = "product.modifiedAt"))
    ProductView mapSourceToView(Product product);
}
