package com.ebookeria.ecommerce.service.category;

import com.ebookeria.ecommerce.dto.author.AuthorCreateDTO;
import com.ebookeria.ecommerce.dto.author.AuthorDTO;
import com.ebookeria.ecommerce.dto.author.AuthorUpdateDTO;
import com.ebookeria.ecommerce.dto.category.CategoryCreateDTO;
import com.ebookeria.ecommerce.dto.category.CategoryDTO;
import com.ebookeria.ecommerce.dto.category.CategoryUpdateDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAll();

    CategoryDTO findById(int id);

    CategoryDTO save(CategoryCreateDTO categoryCreateDTO);

    void update(CategoryUpdateDTO categoryUpdateDTO);

    void deleteById(int id);
}
