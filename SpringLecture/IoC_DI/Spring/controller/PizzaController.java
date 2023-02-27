package com.sparta.springiocdiinit.controller;


import com.sparta.springiocdiinit.entity.Order;
import com.sparta.springiocdiinit.enums.Menu;
import com.sparta.springiocdiinit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    private final OrderService orderService;

    @Autowired
    public PizzaController(@Qualifier("pizza")OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/saveOrder/{amount}")
    public void saveOrder(@PathVariable int amount){
        this.orderService.order(Menu.Pizza, amount);
    }

    @GetMapping("/getOrders")
    public List<Order> getOrders() {
        return this.orderService.getOrders(Menu.Pizza);
    }
}
