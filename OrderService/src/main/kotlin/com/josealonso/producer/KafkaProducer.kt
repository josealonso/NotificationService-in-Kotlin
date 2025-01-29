package com.josealonso.producer

import com.josealonso.entity.OrderDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

// KafkaProducer is merely a wrapper around the KafkaTemplate class.
@Component
class KafkaProducer {

    private val LOGGER: Logger = LoggerFactory.getLogger(KafkaProducer::class.java)

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, OrderDTO>

    fun send(topic: String, payload: OrderDTO) {
        LOGGER.info("Sending $payload to $topic")
        kafkaTemplate.send(topic, payload)
    }
}

