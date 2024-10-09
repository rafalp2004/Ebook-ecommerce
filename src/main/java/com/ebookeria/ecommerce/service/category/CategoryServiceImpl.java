package com.ebookeria.ecommerce.service.category;

import com.ebookeria.ecommerce.dto.category.CategoryCreateDTO;
import com.ebookeria.ecommerce.dto.category.CategoryDTO;
import com.ebookeria.ecommerce.dto.category.CategoryUpdateDTO;
import com.ebookeria.ecommerce.entity.Category;
import com.ebookeria.ecommerce.exception.ResourceNotFoundException;
import com.ebookeria.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream().map((s) -> mapToDTO(s)).toList();

    }

    @Override
    public CategoryDTO findById(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id: " + id + " not found"));
        return mapToDTO(category);
    }

    @Override
    public CategoryDTO save(CategoryCreateDTO categoryCreateDTO) {
        Category category = new Category();
        category.setName(categoryCreateDTO.name());
        categoryRepository.save(category);
        return mapToDTO(category);
    }

    @Override
    public void update(CategoryUpdateDTO categoryUpdateDTO) {
        Category category = categoryRepository.findById(categoryUpdateDTO.id()).orElseThrow(() -> new ResourceNotFoundException("Category with id: " + categoryUpdateDTO.id() + " not found"));

        category.setName(categoryUpdateDTO.name());
        categoryRepository.save(category);

    }

    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);

    }

    private CategoryDTO mapToDTO(Category s) {
        return new CategoryDTO(s.getId(), s.getName());
    }
}
