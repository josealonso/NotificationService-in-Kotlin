package com.josealonso.sending.service

import com.josealonso.consumer.entity.OrderDTO
import jakarta.mail.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PhoneService(order: OrderDTO): SendService {

    val LOGGER = LoggerFactory.getLogger(PhoneService::class.java)
    
    val customerName = order.userId.name
    val recipient = order.userId.email
    val emailSubject = "Your Amazon order has been ${order.status}"

    val emailBody = """
                       Dear $customerName,
                       <br> 
                       Your order number ${order.orderId} has been ${order.status}.    
               """.trimIndent()

    override fun sendMessage(messageData: MessageData, sender: String) {
        val session = configurePhoneSession()

        try {
            // TODO: Send SMS
            LOGGER.info("SMS message sent successfully to $recipient")

        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }

    private fun configurePhoneSession(): Session? {
        // TODO: Configure phone session
        return null
    }

}
