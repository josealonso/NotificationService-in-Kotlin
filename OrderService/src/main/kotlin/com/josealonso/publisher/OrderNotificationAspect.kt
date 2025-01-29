package com.josealonso.com.josealonso.publisher

import com.josealonso.com.josealonso.entity.OrderDTO
import com.josealonso.com.josealonso.entity.OrderStatus
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class OrderNotificationAspect(private val orderNotificationPublisher: OrderNotificationPublisher) {

    @AfterReturning("execution(* com.josealonso.com.josealonso.service.OrderService.createOrder(..)) && args(order)")
    fun afterOrderCreation(order: OrderDTO) {
        orderNotificationPublisher.publishOrderNotification(
            order.orderId,
            order.status,
            NotificationType.EMAIL
        )
    }

    @AfterReturning("execution(* com.yourpackage.OrderService.updateOrderStatus(..)) && args(orderId, newStatus)")
    fun afterOrderStatusUpdate(orderId: Long, newStatus: OrderStatus) {
        orderNotificationPublisher.publishOrderNotification(
            orderId,
            newStatus,
            NotificationType.EMAIL
        )
    }
}
