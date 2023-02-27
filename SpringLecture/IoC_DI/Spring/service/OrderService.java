package com.sparta.springiocdiinit.service;

import com.sparta.springiocdiinit.entity.Order;
import com.sparta.springiocdiinit.enums.Menu;

import java.util.List;


public interface OrderService {
    void order(Menu menu, int amount);
    List<Order> getOrders(Menu menu);
}
