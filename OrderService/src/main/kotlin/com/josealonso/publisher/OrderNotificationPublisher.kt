package com.josealonso.publisher

import com.josealonso.entity.OrderStatus
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OrderNotificationPublisher(private val kafkaTemplate: KafkaTemplate<String, OrderNotification>) {

    fun publishOrderNotification(orderId: Long, status: OrderStatus, notificationType: NotificationType) {
        val notification = OrderNotification(orderId, status, notificationType)
        kafkaTemplate.send("order-notifications", orderId.toString(), notification)
    }
}

// Class shared between microservices
data class OrderNotification(
    val orderId: Long,
    val status: OrderStatus,
    val notificationType: NotificationType
)

enum class NotificationType {
    SMS, EMAIL
}
