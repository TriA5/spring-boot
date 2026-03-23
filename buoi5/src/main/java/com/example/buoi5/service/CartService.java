package com.example.buoi5.service;

import com.example.buoi5.model.Cart;

public interface CartService {
    Cart getOrCreateCart(Long userId);

    Cart getCartByUser(Long userId);

    void addItem(Long userId, Long productId, int quantity);

    void removeItem(Long cartItemId);

    void clearCart(Long userId);
}
