package com.josealonso.listener

import com.josealonso.exceptions.UnknownOrderException

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderNotificationListener {

    @KafkaListener(topics = ["order-notifications"], groupId = "notification-service-group")
    fun listenOrderNotifications(orderNotification: OrderNotification) {
        println("===== AAAAAAAAAA - Received Order Notification: $orderNotification")

        when (orderNotification.status) {
            OrderStatus.PENDING -> handleOrderCreated(orderNotification)
            OrderStatus.PROCESSING -> handleOrderUpdated(orderNotification)
            OrderStatus.CANCELLED -> handleOrderCancelled(orderNotification)
            OrderStatus.COMPLETED -> handleOrderCompleted(orderNotification)
            else -> throw UnknownOrderException(orderNotification)
        }
    }

    private fun handleOrderCreated(notification: OrderNotification) {
        println("===== AAAAAAAAAA - Received creation Order Notification: $notification()")
        // Logic for handling new order creation, e.g., sending confirmation email
    }

    private fun handleOrderUpdated(notification: OrderNotification) {
        println("===== AAAAAAAAAA - Received update Order Notification: $notification()")
        // Logic for handling order updates, e.g., sending status update notification
    }

    private fun handleOrderCancelled(notification: OrderNotification) {
        println("===== AAAAAAAAAA - Received cancel Order Notification: $notification()")
        // Logic for handling cancelled orders, e.g., sending cancellation confirmation
    }

    private fun handleOrderCompleted(notification: OrderNotification) {
        println("===== AAAAAAAAAA - Received complete Order Notification: $notification()")
        // Logic for handling completed orders, e.g., sending completion notification
    }

}

// Shared between microservices
data class OrderNotification(
    val orderId: Long,
    val status: OrderStatus,
    val notificationType: NotificationType
)

enum class NotificationType {
    SMS,
    EMAIL
}

enum class OrderStatus {
    PENDING, PROCESSING, COMPLETED, CANCELLED
}
