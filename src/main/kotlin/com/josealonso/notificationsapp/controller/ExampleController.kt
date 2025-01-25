package com.josealonso.notificationsapp.controller

import com.josealonso.notificationsapp.producer.ExampleStringProducer
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController(
    private val exampleStringProducer: ExampleStringProducer
) {

    @PostMapping("/api/v1/test")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun sendTestMessage(@RequestBody requestBody: RequestBodyDTO) =
        exampleStringProducer.sendStringMessage(message = requestBody.message)
  }

data class RequestBodyDTO(val message: String)