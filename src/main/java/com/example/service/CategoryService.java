package com.example.service;

<<<<<<< HEAD
import java.util.List;

public interface CategoryService {
    List<String> getAll();
=======
import com.example.model.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    public List<String> getAll() {
        ArrayList<String> categories = new ArrayList<>();
        for (ProductCategory category : List.of(ProductCategory.values()))
            categories.add(category.getName());

        return categories;
    }
>>>>>>> 1fd69002eef85d5633ac06464fcb5ba9aa763714
}
