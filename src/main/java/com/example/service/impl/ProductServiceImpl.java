package com.example.service.impl;

import com.example.dto.ProductCreateRequest;
import com.example.dto.ProductDTO;
import com.example.exception.ProductNotFoundException;
import com.example.model.Product;
import com.example.mapper.ProductMapper;
import com.example.model.ProductUpdate;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public ProductDTO get(int id) {
        return repository.findById(id).stream()
                .map(ProductMapper::mapToDTO).findAny()
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public List<ProductDTO> getAll() {
        return repository.findAll().stream().map(ProductMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int create(ProductCreateRequest productCreateRequest) {
        Product product = new Product(
                productCreateRequest.getName(),
                productCreateRequest.getDescription(),
                productCreateRequest.getCategory(),
                productCreateRequest.getPrice());

        if (repository.save(product).getId() == 0)
            throw new RuntimeException("Could not save product");

        return repository.save(product).getId();
    }

    @Override
    @Transactional
    public int update(int id, ProductUpdate productUpd) {
        Product product = repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        product.setName(productUpd.getName());
        product.setDescription(productUpd.getDescription());
        product.setCategory(productUpd.getCategory());
        product.setPrice(product.getPrice());

        return repository.save(product).getId();
    }

    @Override
    @Transactional
    public int updatePrice(int id, double newPrice) {
        Product product = repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        product.setPrice(newPrice);

        return repository.save(product).getId();
    }

    @Override
    @Transactional
    public void delete(int id) {
        Product product = repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        repository.delete(product);
    }
}
