package com.example.buoi5.service;

import com.example.buoi5.model.Order;
import com.example.buoi5.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order placeOrder(Long userId, String shippingAddress);

    List<Order> getOrdersByUser(Long userId);

    List<Order> getAllOrders();

    Optional<Order> findById(Long id);

    Order updateStatus(Long orderId, OrderStatus status);
}
