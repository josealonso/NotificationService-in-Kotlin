package com.josealonso.com.josealonso.controller

import com.josealonso.com.josealonso.service.OrderService
import com.josealonso.com.josealonso.entity.OrderDTO
import com.josealonso.com.josealonso.entity.OrderStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(private val orderService: OrderService) {

    @PostMapping
    fun createOrder(@RequestBody orderDTO: OrderDTO): ResponseEntity<OrderDTO> {
        val createdOrder = orderService.createOrder(orderDTO)
        return ResponseEntity(createdOrder, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable id: Long): ResponseEntity<OrderDTO> {
        val order = orderService.getOrderById(id)
        return ResponseEntity(order, HttpStatus.OK)
    }

    @GetMapping
    fun getAllOrders(): ResponseEntity<List<OrderDTO>> {
        val orders = orderService.getAllOrders()
        return ResponseEntity(orders, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateOrder(@PathVariable id: Long, @RequestBody orderDTO: OrderDTO): ResponseEntity<OrderDTO> {
        val updatedOrder = orderService.updateOrder(id, orderDTO)
        return ResponseEntity(updatedOrder, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable id: Long): ResponseEntity<Unit> {
        orderService.deleteOrder(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PatchMapping("/{id}/status")
    fun updateOrderStatus(@PathVariable id: Long, @RequestBody status: OrderStatus): ResponseEntity<OrderDTO> {
        val updatedOrder = orderService.updateOrderStatus(id, status)
        return ResponseEntity(updatedOrder, HttpStatus.OK)
    }
}
