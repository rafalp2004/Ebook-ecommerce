package com.ebookeria.ecommerce.service.cart;

import com.ebookeria.ecommerce.dto.cart.AddItemToCartDTO;
import com.ebookeria.ecommerce.dto.cart.CartDTO;
import com.ebookeria.ecommerce.dto.cart.RemoveItemFromCartDTO;
import com.ebookeria.ecommerce.dto.cart.UpdateCartItemDTO;
import com.ebookeria.ecommerce.entity.Cart;

public interface CartService {
    void createCart();
    void addItemToCart(AddItemToCartDTO addItemToCartDTO);
    void removeItemFromCart(RemoveItemFromCartDTO removeItemFromCartDTO);
    CartDTO findById(int id);
    void updateCartItemQuantity(UpdateCartItemDTO updateCartItemDTO);
}
