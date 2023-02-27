package com.sparta.springiocdiinit.service;

import com.sparta.springiocdiinit.entity.Order;
import com.sparta.springiocdiinit.enums.Menu;
import com.sparta.springiocdiinit.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("pizza")
public class PizzaService implements OrderService{

    private final OrderRepository orderRepository;

    @Autowired
    public PizzaService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void order(Menu menu, int amount) {
        Order order = new Order(menu.getFoodName(), menu.getPrice(), amount);
        this.orderRepository.save(order);
    }

    @Override
    public List<Order> getOrders(Menu menu) {
        return orderRepository.findAllByFoodName(menu.getFoodName());

    }
}
