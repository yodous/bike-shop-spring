package com.example.service.impl;

import com.example.model.enums.ProductCategory;
import com.example.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    public List<String> getAll() {
        ArrayList<String> categories = new ArrayList<>();
        for (ProductCategory category : List.of(ProductCategory.values()))
            categories.add(category.getValue());

        return categories;
    }
}
