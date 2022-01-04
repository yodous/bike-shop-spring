package com.example.service.impl;

import com.example.dto.CategoryRepresentation;
import com.example.model.enums.ProductCategory;
import com.example.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    public List<CategoryRepresentation> getAll() {
        ArrayList<CategoryRepresentation> categories = new ArrayList<>();

        for (ProductCategory category : List.of(ProductCategory.values()))
            categories.add(new CategoryRepresentation(category.getValue(), category.getImgUrl()));

        return categories;
    }
}
