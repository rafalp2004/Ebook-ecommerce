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
    //TODO Consider where to create cart


    @PostMapping(path = "/cart")
    public ResponseEntity<Void> addItemToCart(@Valid @RequestBody AddItemToCartDTO addItemToCartDTO){
        cartService.addItemToCart(addItemToCartDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/cart")
    public ResponseEntity<Void> removeItemToCart(@Valid @RequestBody RemoveItemFromCartDTO removeItemFromCartDTO){
        cartService.removeItemFromCart(removeItemFromCartDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO Validations that only owner of the cart can have access to that
    @GetMapping(path="/cart/{id}")
    public ResponseEntity<CartDTO> findCartById(@PathVariable int id){
        CartDTO cartDTO = cartService.findById(id);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PutMapping(path = "/cart")
    public ResponseEntity<Void> updateCartItemQuantity(@Valid @RequestBody UpdateCartItemDTO updateCartItemDTO){
        cartService.updateCartItemQuantity(updateCartItemDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
