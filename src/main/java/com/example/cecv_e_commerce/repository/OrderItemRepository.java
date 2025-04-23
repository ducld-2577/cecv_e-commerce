package com.example.cecv_e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cecv_e_commerce.domain.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
