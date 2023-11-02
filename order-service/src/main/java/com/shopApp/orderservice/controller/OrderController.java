package com.shopApp.orderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shopApp.orderservice.dto.OrderRequest;
import com.shopApp.orderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "placeOrderFallback")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {

        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    @GetMapping
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public String getOrders() {

        // orderService.placeOrder(orderRequest);
        return "GET Request";
    }

    public CompletableFuture<String> placeOrderFallback(OrderRequest orderRequest, Exception e) {
        return CompletableFuture.supplyAsync(() -> "Our servers having a problem, please try again later.");
    }

}
