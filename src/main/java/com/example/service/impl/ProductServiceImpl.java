package com.example.service.impl;

import com.example.dto.ProductCreate;
import com.example.dto.ProductDTO;
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
    public ProductDTO getOne(int id) {
        return repository.findById(id).stream()
                .map(ProductMapper::mapToDTO).findAny()
                .orElseThrow(() -> new RuntimeException("Could not find product"));
    }

    @Override
    public List<ProductDTO> getAll() {
        return repository.findAll().stream().map(ProductMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void create(ProductCreate productCreate) {
        Product product = new Product(productCreate.getName(), productCreate.getDescription(),
                productCreate.getCategory(), productCreate.getPrice());

        if (repository.save(product).getId() == 0)
            throw new RuntimeException("Could not save product");
    }

    @Override
    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void updatePrice(int id, double newPrice) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find product"));
        product.setPrice(newPrice);

        repository.save(product);
    }

    @Override
    @Transactional
    public void update(int id, ProductUpdate productUpd) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find product"));
        product.setName(productUpd.getName());
        product.setDescription(productUpd.getDescription());
        product.setCategory(productUpd.getCategory());
        product.setPrice(product.getPrice());

        repository.save(product);
    }

}
