package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.dto.cart.AddItemToCartDTO;
import com.ebookeria.ecommerce.dto.cart.CartDTO;
import com.ebookeria.ecommerce.dto.cart.RemoveItemFromCartDTO;
import com.ebookeria.ecommerce.dto.cart.UpdateCartItemDTO;
import com.ebookeria.ecommerce.service.cart.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(path = "/carts")
    public ResponseEntity<Void> addItemToCart(@Valid @RequestBody AddItemToCartDTO addItemToCartDTO) {
        cartService.addItemToCart(addItemToCartDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/carts/remove-item")
    public ResponseEntity<Void> removeItemToCart(@Valid @RequestBody RemoveItemFromCartDTO removeItemFromCartDTO) {
        cartService.removeItemFromCart(removeItemFromCartDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(path = "/carts")
    public ResponseEntity<CartDTO> getUserCart() {
        CartDTO cartDTO = cartService.getUserCart();
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PutMapping(path = "/carts")
    public ResponseEntity<Void> updateCartItemQuantity(@Valid @RequestBody UpdateCartItemDTO updateCartItemDTO) {
        cartService.updateCartItemQuantity(updateCartItemDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
