package com.josealonso.consumer

import com.josealonso.exceptions.UnknownOrderException
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {

    private val LOGGER: Logger = LoggerFactory.getLogger(KafkaConsumer::class.java)

    @KafkaListener(topics = ["order-notifications"])
    fun receive(consumerRecord: ConsumerRecord<String, OrderNotification>) {
        LOGGER.info("   AAAAAAA ===== Received message: ${consumerRecord.toString()}")
        val payload = consumerRecord.value()
        val orderStatus = payload.status
        when (payload.status) {
            OrderStatus.PENDING -> handleOrderCreated(payload)
            OrderStatus.PROCESSING -> handleOrderUpdated(payload)
            OrderStatus.CANCELLED -> handleOrderCancelled(payload)
            OrderStatus.COMPLETED -> handleOrderCompleted(payload)
            else -> throw UnknownOrderException(payload)
        }
    }

    private fun handleOrderCreated(notification: OrderNotification) {
        LOGGER.info("===== AAAAAAAAAA - Received creation Order Notification: $notification()")
        // Logic for handling new order creation, e.g., sending confirmation email
    }

    private fun handleOrderUpdated(notification: OrderNotification) {
        LOGGER.info("===== AAAAAAAAAA - Received update Order Notification: $notification()")
        // Logic for handling order updates, e.g., sending status update notification
    }

    private fun handleOrderCancelled(notification: OrderNotification) {
        LOGGER.info("===== AAAAAAAAAA - Received cancel Order Notification: $notification()")
        // Logic for handling cancelled orders, e.g., sending cancellation confirmation
    }

    private fun handleOrderCompleted(notification: OrderNotification) {
        LOGGER.info("===== AAAAAAAAAA - Received complete Order Notification: $notification()")
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
