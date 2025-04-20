package com.example.cecv_e_commerce.service;

import com.example.cecv_e_commerce.domain.dto.cart.CartItemRequestCreateDTO;
import com.example.cecv_e_commerce.domain.dto.cart.CartItemRequestUpdateDTO;
import com.example.cecv_e_commerce.domain.dto.cart.CartResponseDTO;
import com.example.cecv_e_commerce.domain.model.Cart;
import com.example.cecv_e_commerce.domain.model.User;

public interface CartService {
    CartResponseDTO getCart();

    CartResponseDTO addToCart(CartItemRequestCreateDTO cartItemRequestDTO);

    CartResponseDTO removeFromCart(Integer productId);

    CartResponseDTO updateCartItem(Integer productId, CartItemRequestUpdateDTO cartItemRequestUpdateDTO);

    CartResponseDTO clearCart();

    Cart createCart(User user);
}
