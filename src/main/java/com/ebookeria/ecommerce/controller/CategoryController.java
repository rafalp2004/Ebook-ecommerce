package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.dto.category.CategoryCreateDTO;
import com.ebookeria.ecommerce.dto.category.CategoryDTO;
import com.ebookeria.ecommerce.dto.category.CategoryUpdateDTO;
import com.ebookeria.ecommerce.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path="/categories")
    public ResponseEntity<List<CategoryDTO>> findCategories() {
        List<CategoryDTO> categories = categoryService.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("categories/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable int id) {
        CategoryDTO categoryDTO = categoryService.findById(id);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @PostMapping(path="/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryCreateDTO categoryCreateDTO) {
        CategoryDTO createdCategory = categoryService.save(categoryCreateDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @DeleteMapping(path="/categories/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable int id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path="/categories")
    public ResponseEntity<CategoryUpdateDTO> updateCategory(@Valid @RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        categoryService.update(categoryUpdateDTO);
        return new ResponseEntity<>(categoryUpdateDTO, HttpStatus.OK);
    }
}

