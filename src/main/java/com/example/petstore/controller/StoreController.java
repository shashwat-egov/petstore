package com.example.petstore.controller;

import com.example.petstore.model.Order;
import com.example.petstore.model.PetStatus;
import com.example.petstore.service.OrderService;
import com.example.petstore.service.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/store")
@Validated
public class StoreController {
    private final OrderService orderService;
    private final PetService petService;

    public StoreController(OrderService orderService, PetService petService) {
        this.orderService = orderService;
        this.petService = petService;
    }

    @GetMapping("/inventory")
    public Map<String, Integer> getInventory() {
        Map<String, Integer> map = new HashMap<>();
        for (PetStatus status : PetStatus.values()) {
            map.put(status.name(), petService.findByStatus(status).size());
        }
        return map;
    }

    @PostMapping("/order")
    public ResponseEntity<Order> placeOrder(@Valid @RequestBody Order order) {
        return ResponseEntity.ok(orderService.save(order));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        return orderService.findById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.delete(orderId);
        return ResponseEntity.ok().build();
    }
}
