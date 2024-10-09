package com.ebookeria.ecommerce.service.ebook;

import com.ebookeria.ecommerce.dto.category.CategoryUpdateDTO;
import com.ebookeria.ecommerce.dto.ebook.EbookCreationDTO;
import com.ebookeria.ecommerce.dto.ebook.EbookDTO;
import com.ebookeria.ecommerce.dto.ebook.EbookUpdateDTO;

import java.util.List;

public interface EbookService {

    List<EbookDTO> findAll();

    EbookDTO findById(int id);

    EbookDTO save(EbookCreationDTO ebookCreationDTO);

    void update(EbookUpdateDTO ebookUpdateDTO);

    void deleteById(int id);
}
