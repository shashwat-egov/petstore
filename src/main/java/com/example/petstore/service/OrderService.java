package com.example.petstore.service;

import com.example.petstore.model.Order;
import com.example.petstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
