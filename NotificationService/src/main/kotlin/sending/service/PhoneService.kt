package com.josealonso.sending.service

import com.josealonso.entity.OrderDTO
import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.Properties

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

    override fun sendMessage(message: String, sender: String) {
        val session = configurePhoneSession()

        try {
            ; // TODO: Send SMS
            }

            // Transport.send(emailContent)
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
