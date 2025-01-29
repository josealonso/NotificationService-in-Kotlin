package com.josealonso.producer

import com.josealonso.entity.OrderDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

// KafkaProducer is merely a wrapper around the KafkaTemplate class.
@Component
class KafkaProducer {

    private val LOGGER: Logger = LoggerFactory.getLogger(KafkaProducer::class.java)

    @Value("\${app.topic.producer}")
    private lateinit var topic: String

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, OrderDTO>

    fun send(@Payload data: OrderDTO) {
        LOGGER.info("Sending ${data.toString()} to $topic")
        kafkaTemplate.send(topic, data)
    }
}

