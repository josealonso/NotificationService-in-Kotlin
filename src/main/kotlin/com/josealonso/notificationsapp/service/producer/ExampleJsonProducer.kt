package com.josealonso.notificationsapp.service.producer

import com.josealonso.notificationsapp.entities.dto.ExampleDto
import com.josealonso.notificationsapp.entities.dto.UserDto
import com.josealonso.notificationsapp.utilities.TOPIC_NAME_THREE
import com.josealonso.notificationsapp.utilities.TOPIC_NAME_TWO
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ExampleJsonProducer(
    private val exampleDtoKafkaTemplate: KafkaTemplate<String, ExampleDto>,
    private val userDtoKafkaTemplate: KafkaTemplate<String, UserDto>
) {

    fun sendExampleDtoMessage(dto: ExampleDto) =
        exampleDtoKafkaTemplate.send(TOPIC_NAME_TWO, dto)

    fun sendUserDtoMessage(dto: UserDto) =
        userDtoKafkaTemplate.send(TOPIC_NAME_THREE, dto)

}