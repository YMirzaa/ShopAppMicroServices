package com.shopApp.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopApp.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
