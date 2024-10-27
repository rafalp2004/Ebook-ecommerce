package com.ebookeria.ecommerce.service.ebook;

import com.ebookeria.ecommerce.dto.ebook.*;

public interface EbookService {

    EbookResponse findAll(int pageNo, int pageSize,String sortField,String sortDirection);

    EbookDTO findById(int id);

    EbookUserPanelResponse findUsersBook(int pageNo, int pageSize, String sortField, String sortDirection);

    EbookDTO save(EbookCreationDTO ebookCreationDTO);

    void update(EbookUpdateDTO ebookUpdateDTO);

    void deleteById(int id);
}
