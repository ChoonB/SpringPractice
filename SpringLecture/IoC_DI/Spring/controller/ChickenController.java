package com.sparta.springiocdiinit.controller;

import com.sparta.springiocdiinit.entity.Order;
import com.sparta.springiocdiinit.enums.Menu;
import com.sparta.springiocdiinit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chicken")
public class ChickenController {

    private final OrderService orderService;

    @Autowired
    public ChickenController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/saveOrder/{amount}")
    public void saveOrder(@PathVariable int amount){
        this.orderService.order(Menu.Chicken, amount);

    }

    @GetMapping("/getOrders")
    public List<Order> getOrders() {
        return orderService.getOrders(Menu.Chicken);
    }




}
