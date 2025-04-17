package com.example.cecv_e_commerce.service;

import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import lombok.*;
import com.example.cecv_e_commerce.domain.dto.cart.CartResponseDTO;
import com.example.cecv_e_commerce.domain.dto.cart.CartItemDTO;
import com.example.cecv_e_commerce.domain.dto.product.ProductDTO;
import com.example.cecv_e_commerce.domain.model.Cart;
import com.example.cecv_e_commerce.domain.model.CartItem;
import com.example.cecv_e_commerce.domain.model.Product;
import com.example.cecv_e_commerce.repository.CartRepository;
import com.example.cecv_e_commerce.repository.ProductRepository;
import com.example.cecv_e_commerce.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    // TODO: Wait feature JWT to get Current User
    public CartResponseDTO getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        return mapToCartResponseDTO(cart);
    }

    private CartResponseDTO mapToCartResponseDTO(Cart cart) {
        return new CartResponseDTO(cart.getId(), cart.getUser().getId(),
                cart.getItems().stream().map(this::mapToCartItemDTO).collect(Collectors.toList()),
                cart.getItems().size());
    }

    private CartItemDTO mapToCartItemDTO(CartItem cartItem) {
        Product product = cartItem.getProduct();
        ProductDTO productDTO = new ProductDTO(product.getId(), product.getName(),
                product.getDescription(), product.getPrice(), product.getStock());

        return new CartItemDTO(cartItem.getId(), cartItem.getQuantity(), productDTO);
    }

    // TODO: Wait feature JWT to get Current User
    public CartResponseDTO addToCart(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        cart.getItems().add(cartItem);
        cartRepository.save(cart);

        return mapToCartResponseDTO(cart);
    }

    // TODO: Wait feature JWT to get Current User
    public CartResponseDTO removeFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);

        return mapToCartResponseDTO(cart);
    }

    // TODO: Wait feature JWT to get Current User
    public CartResponseDTO updateCartItem(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return mapToCartResponseDTO(cart);
    }
}
