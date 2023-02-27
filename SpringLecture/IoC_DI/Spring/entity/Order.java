package com.sparta.springiocdiinit.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foodName;
    private int price;
    private int amount;

    public Order() {
    }

    public Order(String foodName, int price, int amount) {
        this.foodName = foodName;
        this.price = price;
        this.amount = amount;
    }
}