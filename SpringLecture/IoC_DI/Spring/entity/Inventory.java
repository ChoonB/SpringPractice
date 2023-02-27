package com.sparta.springiocdiinit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@NoArgsConstructor
@Entity
public class Inventory {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String ingredient;
   private int amount;

    public Inventory(String ingredient, int amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }
}