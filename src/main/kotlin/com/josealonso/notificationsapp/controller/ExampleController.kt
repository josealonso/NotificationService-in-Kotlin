package com.josealonso.notificationsapp.controller

import com.josealonso.notificationsapp.entities.dto.ExampleDto
import com.josealonso.notificationsapp.entities.dto.UserDto
import com.josealonso.notificationsapp.service.producer.ExampleJsonProducer
import com.josealonso.notificationsapp.service.producer.ExampleStringProducer
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class ExampleController(
    private val exampleStringProducer: ExampleStringProducer,
    private val exampleJsonProducer: ExampleJsonProducer
) {

    @PostMapping("/api/v1/test")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun sendTestMessage(@RequestBody requestBody: RequestBodyDTO) {
        exampleStringProducer.sendStringMessage(message = requestBody.message)
        exampleJsonProducer.sendExampleDtoMessage(
            dto = ExampleDto(requestBody.message)
        )
        exampleJsonProducer.sendUserDtoMessage(
            dto = UserDto(
                id = Random.nextLong(0, 100),
                name = requestBody.message
            )
        )
    }

  }

data class RequestBodyDTO(val message: String)