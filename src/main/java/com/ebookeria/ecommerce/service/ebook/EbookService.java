package com.ebookeria.ecommerce.service.ebook;

import com.ebookeria.ecommerce.dto.ebook.EbookCreationDTO;
import com.ebookeria.ecommerce.dto.ebook.EbookDTO;
import com.ebookeria.ecommerce.dto.ebook.EbookResponse;
import com.ebookeria.ecommerce.dto.ebook.EbookUpdateDTO;

public interface EbookService {

    EbookResponse findAll(int pageNo, int pageSize);

    EbookDTO findById(int id);

    EbookDTO save(EbookCreationDTO ebookCreationDTO);

    void update(EbookUpdateDTO ebookUpdateDTO);

    void deleteById(int id);
}
