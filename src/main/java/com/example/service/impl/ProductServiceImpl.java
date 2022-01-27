package com.example.service.impl;

import com.example.dto.ProductRepresentation;
import com.example.dto.ProductsResponse;
import com.example.exception.ProductNotFoundException;
import com.example.mapper.ProductViewMapper;
import com.example.model.enums.ProductCategory;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductViewMapper productViewMapper;

    @Override
    public ProductRepresentation get(int id) {
        return productRepository.findById(id)
                .stream().map(productViewMapper::mapSourceToView).findAny()
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public ProductsResponse getAll(int page, int size) {
        List<ProductRepresentation> products = productRepository.findAll(PageRequest.of(page, size))
                .stream().map(productViewMapper::mapSourceToView)
                .collect(Collectors.toList());
        return new ProductsResponse(products, products.size());
    }

    @Override
    public ProductsResponse getByCategoryPaginated(String categoryName, int page, int size) {
        ProductCategory category = productViewMapper.getProductCategory(categoryName);

        List<ProductRepresentation> products =
                productRepository.findAllByCategoryWithPagination(category, PageRequest.of(page, size))
                .stream().map(productViewMapper::mapSourceToView)
                .collect(Collectors.toList());
        return new ProductsResponse(products, products.size());
    }

}
