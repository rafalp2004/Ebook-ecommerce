package com.ebookeria.ecommerce.dto.ebook;

public record EbookUserPanelDTO(
        int id,
        String title,
        String imageUrl, //First one
        String downloadUrl
) {
}
