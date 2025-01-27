package com.josealonso.com.josealonso.service

import com.josealonso.com.josealonso.entity.OrderDTO
import com.josealonso.com.josealonso.entity.OrderStatus

// interface OrderService(private val orderRepository: OrderRepository) {
interface OrderService {
    fun createOrder(orderDTO: OrderDTO): OrderDTO
    fun getOrderById(id: Long): OrderDTO
    fun getAllOrders(): List<OrderDTO>
    fun updateOrder(id: Long, orderDTO: OrderDTO): OrderDTO
    fun deleteOrder(id: Long)
    fun updateOrderStatus(id: Long, status: OrderStatus): OrderDTO
}


