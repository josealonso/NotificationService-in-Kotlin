package com.josealonso.com.josealonso.service

import com.josealonso.com.josealonso.repository.OrderRepository
import com.josealonso.com.josealonso.entity.OrderDTO
import com.josealonso.com.josealonso.entity.OrderStatus
import com.josealonso.com.josealonso.exceptions.OrderNotFoundException
import com.josealonso.com.josealonso.extensions.copy
import com.josealonso.com.josealonso.extensions.fromDTO
import com.josealonso.com.josealonso.extensions.toDTO
import org.springframework.stereotype.Service
import org.springframework.data.repository.findByIdOrNull

@Service
class OrderServiceImpl(private val orderRepository: OrderRepository) {

    fun createOrder(orderDTO: OrderDTO): OrderDTO {
        val order = orderDTO.fromDTO()
        order.status = OrderStatus.PROCESSING
        val savedOrder = orderRepository.save(order)
        return savedOrder.toDTO()
    }

    fun getOrderById(id: Long): OrderDTO {
        val order = orderRepository.findByIdOrNull(id) ?: throw OrderNotFoundException(id)
        return order.toDTO()
    }

    fun getAllOrders(): List<OrderDTO> {
        return orderRepository.findAll().map { it.toDTO() }
    }

    fun updateOrder(id: Long, orderDTO: OrderDTO): OrderDTO {
        val existingOrder = orderRepository.findByIdOrNull(id) ?: throw OrderNotFoundException(id)
        val updatedOrder = existingOrder.copy()
        return orderRepository.save(updatedOrder).toDTO()
    }

    fun deleteOrder(id: Long) {
        if (!orderRepository.existsById(id)) {
            throw OrderNotFoundException(id)
        }
        orderRepository.deleteById(id)
    }

    fun updateOrderStatus(id: Long, status: OrderStatus): OrderDTO {
        val order = orderRepository.findByIdOrNull(id) ?: throw OrderNotFoundException(id)
        val updatedOrder = order.copy()
        updatedOrder.status = status
        return orderRepository.save(updatedOrder).toDTO()
    }

}

