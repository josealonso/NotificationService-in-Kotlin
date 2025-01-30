package com.josealonso.sending.service

import com.josealonso.consumer.entity.OrderDTO
import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.Properties

class MessageData(
    val recipient : String,
    val emailSubject : String,
    val emailBody : String
)

@Component
class EmailService(order: OrderDTO): SendService {

    val LOGGER = LoggerFactory.getLogger(EmailService::class.java)
    

    override fun sendMessage(messageData: MessageData, sender: String) {
        val session = configureEmailSession()

        try {
            val emailContent = MimeMessage(session).apply {
                setFrom(InternetAddress(sender))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(messageData.recipient))
                setSubject(messageData.emailSubject)
                setText(messageData.emailBody, "utf-8")
            }

            Transport.send(emailContent)
            LOGGER.info("Email sent successfully to $messageData.recipient")
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }

    private fun composeMessageBody(order: OrderDTO): String {
        return """
                       Dear $order.userId.name,
                       <br> 
                       Your order number ${order.orderId} has been ${order.status}.    
               """.trimIndent()
    }

    private fun configureEmailSession(): Session {
        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.port", "587")
        }

        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("your_email@gmail.com", "your_password")
            }
        })
        return session
    }

}
