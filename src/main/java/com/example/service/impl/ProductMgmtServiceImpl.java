package com.example.service.impl;

import com.example.dto.ProductRequest;
import com.example.exception.ProductNotFoundException;
import com.example.mapper.ProductViewMapper;
import com.example.model.Product;
import com.example.repository.OrderItemRepository;
import com.example.repository.ProductRepository;
import com.example.service.AuthService;
import com.example.service.ProductMgmtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductMgmtServiceImpl implements ProductMgmtService {
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductViewMapper productViewMapper;
    private final AuthService authService;

    @Override
    public int save(ProductRequest productRequest) {
        Product product = productViewMapper.mapProductDtoToSource(
                productRequest, authService.getCurrentUser());
        return productRepository.save(product).getId();
    }

    @Override
    public int update(int id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(productViewMapper.getProductCategory(request.getCategory()));
        product.setPrice(request.getPrice());

        return productRepository.save(product).getId();
    }

    @Override
    public int refreshDate(int id) {
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException(id));
        product.setModifiedAt(Instant.now());

        return productRepository.save(product).getId();
    }

    @Override
    @Transactional
    public void delete(int id) {
        orderItemRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

}
