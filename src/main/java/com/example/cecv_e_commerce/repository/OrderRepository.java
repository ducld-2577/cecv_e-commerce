package com.example.cecv_e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cecv_e_commerce.domain.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
