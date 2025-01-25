package com.josealonso.notificationsapp.service.consumer

import com.josealonso.notificationsapp.utilities.GROUP_ID
import com.josealonso.notificationsapp.utilities.TOPIC_NAME
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ExampleConsumer {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(topics = [TOPIC_NAME], groupId = GROUP_ID)
    fun firstListener(message: String) =
        logger.info("Message received: [$message]")

}
