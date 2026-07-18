package com.example.Spring_Ai2.repository;


import com.example.Spring_Ai2.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByProductId(String productId);
    void deleteByProductId(String productId);
}