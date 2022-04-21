package com.shibuyaxpress.photobackend.controllers

import com.shibuyaxpress.photobackend.OrderRepository
import com.shibuyaxpress.photobackend.models.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = ["*"])
class OrderController(private var orderRepository: OrderRepository) {

    @GetMapping("/")
    fun getOrders(): List<Order> {
        return orderRepository.findAll()
    }

    @PostMapping("/")
    fun createOrder(order: Order): Order {
        return orderRepository.save(order)
    }

    @PutMapping("/")
    fun updateOrder(order: Order): Order {
        try {
            var current = orderRepository.findById(order.id!!)
            if (current.isPresent) {
                return orderRepository.save(order)
            }
            return ResponseEntity.badRequest().build<Order>().body!!
        } catch (e: Exception) {
            return ResponseEntity.notFound().build<Order>().body!!
        }
    }
}