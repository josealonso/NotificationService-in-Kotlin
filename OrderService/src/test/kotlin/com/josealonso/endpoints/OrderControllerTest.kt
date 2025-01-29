package com.josealonso.endpoints

import com.josealonso.controller.OrderController
import com.josealonso.entity.OrderDTO
import com.josealonso.entity.OrderStatus
import com.josealonso.entity.UserDTO
import com.josealonso.service.OrderServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import com.josealonso.entity.NotificationType

import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal

@WebMvcTest
@ContextConfiguration(classes = [OrderController::class])
class OrderControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    // @MockBean   // deprecated since Spring Boot 3.4.0
    @MockitoBean
    private lateinit var orderService: OrderServiceImpl

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    val orderDTOExample = OrderDTO(
        orderId = 1L,
        name = "Order 1",
        price = BigDecimal(100.0),
        status = OrderStatus.PENDING,
        userId = UserDTO(
            userId = 1L,
            name = "User 1",
            email = "alice@example.com",
            phoneNumber = "1234567890",
            orders = mutableListOf()
        ),
        notificationType = NotificationType.EMAIL
    )

    @Test
    fun `should create user`() {
        val orderDTO = orderDTOExample

        `when`(orderService.createOrder(orderDTO)).thenReturn(orderDTO)

        mockMvc.perform(post("/api/v1/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(orderDTO)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.orderId").value(orderDTO.orderId))
            .andExpect(jsonPath("$.name").value(orderDTO.name))
            .andExpect(jsonPath("$.price").value(orderDTO.price))
            // .andExpect(jsonPath("$.status").value(orderDTO.status))
            .andExpect(jsonPath("$.userId.userId").value(orderDTO.userId.userId))
            .andExpect(jsonPath("$.userId.name").value(orderDTO.userId.name))
            .andExpect(jsonPath("$.userId.email").value(orderDTO.userId.email))
            .andExpect(jsonPath("$.userId.phoneNumber").value(orderDTO.userId.phoneNumber))
            .andExpect(jsonPath("$.userId.orders").isEmpty)
    }


}
