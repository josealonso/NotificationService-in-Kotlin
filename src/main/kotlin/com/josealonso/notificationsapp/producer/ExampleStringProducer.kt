package com.josealonso.notificationsapp.producer

import com.josealonso.notificationsapp.utilities.TOPIC_NAME
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class ExampleStringProducer (
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    fun sendStringMessage(message: String) =
        kafkaTemplate.send(TOPIC_NAME, message)
}