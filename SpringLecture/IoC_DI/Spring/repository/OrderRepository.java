package com.sparta.springiocdiinit.repository;

import com.sparta.springiocdiinit.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByFoodName(String foodName);
}

