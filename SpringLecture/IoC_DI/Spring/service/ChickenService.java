package com.sparta.springiocdiinit.service;

import com.sparta.springiocdiinit.entity.Inventory;
import com.sparta.springiocdiinit.entity.Order;
import com.sparta.springiocdiinit.enums.Menu;
import com.sparta.springiocdiinit.repository.InventoryRepository;
import com.sparta.springiocdiinit.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class ChickenService implements OrderService{

    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;

//    @Autowired
//    public ChickenService(OrderRepository orderRepository, InventoryRepository inventoryRepository) {
//        this.orderRepository = orderRepository;
//        this.inventoryRepository = inventoryRepository;
//    }

    @Override
    public void order(Menu menu, int amount) {
        Order order = new Order(menu.getFoodName(), menu.getPrice(), amount);
        List<Inventory> all = inventoryRepository.findAll();
        orderRepository.save(order);

    }

    @Override
    public List<Order> getOrders(Menu menu) {

        List<Order> allByFoodName = orderRepository.findAllByFoodName(menu.getFoodName());
        for (Order order : allByFoodName) {
            System.out.println(order.getFoodName());
            System.out.println(order.getPrice());
            System.out.println(order.getAmount());
        }

        return allByFoodName;
    }
}
