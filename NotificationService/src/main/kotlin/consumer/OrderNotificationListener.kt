package com.josealonso.consumer

import com.josealonso.consumer.entity.OrderDTO
import com.josealonso.exceptions.UnknownOrderException
import com.josealonso.sending.service.EmailService
import com.josealonso.sending.service.MessageData
import com.josealonso.sending.service.PhoneService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {

    private val LOGGER: Logger = LoggerFactory.getLogger(KafkaConsumer::class.java)

    @KafkaListener(topics = ["order-notifications"])
    fun receive(consumerRecord: ConsumerRecord<String, OrderDTO>) {
        LOGGER.info("   AAAAAAA ===== Received message: ${consumerRecord.toString()}")
        val payload = consumerRecord.value()
        val orderDTO = payload.copy()
        // add a field to the payload
        val notificationType = NotificationType.EMAIL
        when (orderDTO.notificationType) {
            NotificationType.EMAIL -> EmailService(orderDTO).sendMessage(
                prepareData(orderDTO), "no-reply@amazon.com")
            NotificationType.SMS -> PhoneService(orderDTO).sendMessage(
                prepareData(orderDTO), "123333444"
            )
            else -> throw UnknownOrderException(OrderNotification)
        }
    }

    fun prepareData(order: OrderDTO): MessageData {
        val customerName = order.userId.name
        val recipient = order.userId.email
        val emailSubject = "Your Amazon order has been ${order.status}"

        val emailBody = """
                       Dear $customerName,
                       <br> 
                       Your order number ${order.orderId} has been ${order.status}.    
               """.trimIndent()
        return MessageData(recipient, emailSubject, emailBody)
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
    PENDING, PROCESSED, COMPLETED, CANCELLED
}
