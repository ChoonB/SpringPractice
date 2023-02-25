package com.sparta.springjpa.repository;

import com.sparta.springjpa.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
