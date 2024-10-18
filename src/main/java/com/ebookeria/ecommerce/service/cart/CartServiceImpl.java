package com.ebookeria.ecommerce.service.cart;

import com.ebookeria.ecommerce.dto.cart.*;
import com.ebookeria.ecommerce.entity.Cart;
import com.ebookeria.ecommerce.entity.CartItem;
import com.ebookeria.ecommerce.entity.Ebook;
import com.ebookeria.ecommerce.entity.User;
import com.ebookeria.ecommerce.exception.ResourceNotFoundException;
import com.ebookeria.ecommerce.exception.UnauthorizedAccessException;
import com.ebookeria.ecommerce.repository.CartRepository;
import com.ebookeria.ecommerce.repository.EbookRepository;
import com.ebookeria.ecommerce.repository.UserRepository;
import com.ebookeria.ecommerce.service.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final UserService userService;
    private final CartRepository cartRepository;
    private final EbookRepository ebookRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(UserService userService, CartRepository cartRepository, EbookRepository ebookRepository, UserRepository userRepository) {
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.ebookRepository = ebookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createCart(int id) {
        Cart cart = new Cart();
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User with id: " + id + " not found"));
        cart.setUser(user);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setLastUpdated(LocalDateTime.now());

        cartRepository.save(cart);
    }

    @Override
    public void addItemToCart(AddItemToCartDTO addItemToCartDTO) {
        User user = userService.getCurrentUser();

        int ebookId = addItemToCartDTO.ebookId();
        int quantity = addItemToCartDTO.quantity();
        Cart cart = cartRepository.findByUserId(user.getId());
        Ebook ebook = ebookRepository.findById(ebookId).orElseThrow(() -> new ResourceNotFoundException("Ebook with id" + ebookId + " not found"));

        verifyUserHasAccess(cart);

        Optional<CartItem> existingCartItem = cart.getCartItems().stream().filter(item -> item.getEbook().getId() == ebookId).findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {


            CartItem cartItem = new CartItem();
            cartItem.setEbook(ebook);
            cartItem.setQuantity(quantity);
            cartItem.setUnit_price(ebook.getPrice());
            cartItem.setCart(cart);
            cartItem.setAddedAt(LocalDateTime.now());

            cart.addCartItem(cartItem);
            cart.setLastUpdated(LocalDateTime.now());

            cartRepository.save(cart);
        }
    }

    @Override
    public void removeItemFromCart(RemoveItemFromCartDTO removeItemFromCartDTO) {
        int cartId = removeItemFromCartDTO.cartId();
        int cartItemId = removeItemFromCartDTO.cartItemId();
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart with id" + cartId + " not found"));

        verifyUserHasAccess(cart);

        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getId() == cartItemId).findFirst().orElseThrow(() -> new RuntimeException("CartItem not found"));
        cart.getCartItems().remove(cartItem);

        cart.setLastUpdated(LocalDateTime.now());

        cartRepository.save(cart);

    }


    @Override
    public CartDTO findById(int id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart with id" + id + " not found"));

        verifyUserHasAccess(cart);
        return mapCartToDTO(cart);
    }

    @Override
    public CartDTO getUserCart() {

        User currentUser = userService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(currentUser.getId());
        verifyUserHasAccess(cart);
        return mapCartToDTO(cart);

    }


    private CartDTO mapCartToDTO(Cart cart) {
        return new CartDTO(cart.getId(),
                cart.getCreatedAt(),
                cart.getLastUpdated(),
                cart.getCartItems().stream().map(this::mapCartItemToDTO).toList()
        );


    }


    private CartItemDTO mapCartItemToDTO(CartItem cartItem) {
        return new CartItemDTO(cartItem.getId(), cartItem.getEbook().getId(), cartItem.getQuantity(), cartItem.getUnit_price(), cartItem.getAddedAt());
    }

    @Override
    public void updateCartItemQuantity(UpdateCartItemDTO updateCartItemDTO) {
        User user = userService.getCurrentUser();

        int cartItemId = updateCartItemDTO.cartItemId();
        int newQuantity = updateCartItemDTO.quantity();
        Cart cart = cartRepository.findByUserId(user.getId());


        verifyUserHasAccess(cart);
        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getId() == cartItemId).findFirst().orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItem.setQuantity(newQuantity);

        cartRepository.save(cart);
    }



    private void verifyUserHasAccess(Cart cart) {
        User currentUser = userService.getCurrentUser();

        if (cart.getUser().getId() != currentUser.getId() && !userService.isCurrentUserAdmin()) {
            throw new UnauthorizedAccessException("You do not have permission to modify this cart.");
        }
    }
}
