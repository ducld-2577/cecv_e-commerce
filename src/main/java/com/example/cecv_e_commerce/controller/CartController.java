package com.example.cecv_e_commerce.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.cecv_e_commerce.domain.dto.cart.CartResponseDTO;
import com.example.cecv_e_commerce.service.CartService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartResponseDTO getCart(@RequestParam Long userId) {
        return cartService.getCart(userId);
    }

    @PostMapping("/add")
    public CartResponseDTO addToCart(@RequestParam Long userId, @RequestParam Long productId,
            @RequestParam int quantity) {
        return cartService.addToCart(userId, productId, quantity);
    }

    @DeleteMapping("/product/{productId}")
    public CartResponseDTO removeFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        return cartService.removeFromCart(userId, productId);
    }

    @PutMapping("/update")
    public CartResponseDTO updateCartItem(@RequestParam Long userId, @RequestParam Long productId,
            @RequestParam int quantity) {
        return cartService.updateCartItem(userId, productId, quantity);
    }
}
