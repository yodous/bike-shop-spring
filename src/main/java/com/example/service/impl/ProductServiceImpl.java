package com.example.service.impl;

import com.example.dto.ProductRequest;
import com.example.dto.ProductView;
import com.example.exception.ProductNotFoundException;
import com.example.model.Product;
import com.example.mapper.ProductViewMapper;
import com.example.model.enums.ProductCategory;
import com.example.repository.OrderItemRepository;
import com.example.repository.ProductRepository;
import com.example.service.AuthService;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductViewMapper productViewMapper;
    private final AuthService authService;

    @Override
    public ProductView get(int id) {
        return productRepository.findById(id)
                .stream().map(productViewMapper::mapSourceToView).findAny()
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public List<ProductView> getAll(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size))
                .stream().map(productViewMapper::mapSourceToView)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductView> getByNamePaginated(String productName, int page, int size) {
        String trimmed = productName.trim();

        return productRepository.findAll(PageRequest.of(page, size))
                .stream().filter(p -> StringUtils.containsIgnoreCase(p.getName(), trimmed))
                .map(productViewMapper::mapSourceToView)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductView> getByCategoryPaginated(String categoryName, int page, int size) {
        ProductCategory category = productViewMapper.getProductCategory(categoryName);

        return productRepository.findAllByCategoryWithPagination(category, PageRequest.of(page, size))
                .stream().map(productViewMapper::mapSourceToView)
                .collect(Collectors.toList());
    }

    @Override
    public int save(ProductRequest productRequest) {
        Product product = productViewMapper.mapProductDtoToSource(
                productRequest, authService.getCurrentUser());
        return productRepository.save(product).getId();
    }

    @Override
    @Transactional
    public int update(int id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        product.setName(request.getName());
        product.setDescription(request.getName());
        product.setCategory(productViewMapper.getProductCategory(request.getCategory()));
        product.setPrice(request.getPrice());

        return id;
    }

    @Override
    public int refreshDate(int id) {
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException(id));
        product.setModifiedAt(Instant.now());
        productRepository.save(product);
        return product.getId();
    }

    @Override
    @Transactional
    public void delete(int id) {
        orderItemRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    @Override
    public int count() {
        return productRepository.findAll().size();
    }
}
