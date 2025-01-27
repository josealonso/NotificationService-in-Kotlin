package com.josealonso.controller

import com.josealonso.com.josealonso.controller.OrderController
import com.josealonso.com.josealonso.entity.OrderDTO
import com.josealonso.com.josealonso.entity.OrderStatus
import com.josealonso.com.josealonso.entity.UserDTO
import com.josealonso.com.josealonso.service.OrderServiceImpl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import kotlin.test.assertEquals

class OrderControllerTest {

    private lateinit var orderService: OrderServiceImpl
    private lateinit var orderController: OrderController

    private val orderDTOExample = OrderDTO(
        orderId = 1L,
        name = "name",
        price = BigDecimal.ONE,
        status = OrderStatus.PENDING,
        userId = UserDTO(
            userId = 1,
            name = "user name",
            email = "example@gmail.com",
            phoneNumber = "123456789",
            orders = mutableListOf(),
        ),
    )

    private val orderDTOExample2 = OrderDTO(
        orderId = 2L,
        name = "name2",
        price = BigDecimal(2),
        status = OrderStatus.PENDING,
        userId = UserDTO(
            userId = 2,
            name = "user2 name",
            email = "example2@gmail.com",
            phoneNumber = "133456789",
            orders = mutableListOf(),
        ),
    )

    @BeforeEach
    fun setup() {
        orderService = mockk()
        orderController = OrderController(orderService)
    }

    @Test
    fun `createOrder should return created order with CREATED status`() {
        val orderDTO = orderDTOExample
        every { orderService.createOrder(any()) } returns orderDTO

        val result = orderController.createOrder(orderDTO)

        assertEquals(HttpStatus.CREATED, result.statusCode)
        assertEquals(orderDTO, result.body)
        verify(exactly = 1) { orderService.createOrder(orderDTO) }
    }

    @Test
    fun `getOrder should return order with OK status`() {
        val orderId = 1L
        val orderDTO = orderDTOExample
        every { orderService.getOrderById(orderId) } returns orderDTO

        val result = orderController.getOrder(orderId)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(orderDTO, result.body)
        verify(exactly = 1) { orderService.getOrderById(orderId) }
    }

    @Test
    fun `getAllOrders should return list of orders with OK status`() {
        val orders = listOf(
            orderDTOExample, orderDTOExample2
        )
        every { orderService.getAllOrders() } returns orders

        val result = orderController.getAllOrders()

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(orders, result.body)
        verify(exactly = 1) { orderService.getAllOrders() }
    }

    @Test
    fun `updateOrder should return updated order with OK status`() {
        val orderId = 1L
        val orderDTO = orderDTOExample
        every { orderService.updateOrder(orderId, orderDTO) } returns orderDTO

        val result = orderController.updateOrder(orderId, orderDTO)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(orderDTO, result.body)
        verify(exactly = 1) { orderService.updateOrder(orderId, orderDTO) }
    }

    @Test
    fun `deleteOrder should return NO_CONTENT status`() {
        val orderId = 1L
        every { orderService.deleteOrder(orderId) } returns Unit

        val result = orderController.deleteOrder(orderId)

        assertEquals(HttpStatus.NO_CONTENT, result.statusCode)
        verify(exactly = 1) { orderService.deleteOrder(orderId) }
    }

    @Test
    fun `updateOrderStatus should return updated order with OK status`() {
        val orderId = 1L
        val newStatus = OrderStatus.COMPLETED
        val updatedOrderDTO = orderDTOExample.copy()
        updatedOrderDTO.status = newStatus
        every { orderService.updateOrderStatus(orderId, newStatus) } returns updatedOrderDTO

        val result = orderController.updateOrderStatus(orderId, newStatus)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(updatedOrderDTO, result.body)
        verify(exactly = 1) { orderService.updateOrderStatus(orderId, newStatus) }
    }
}
