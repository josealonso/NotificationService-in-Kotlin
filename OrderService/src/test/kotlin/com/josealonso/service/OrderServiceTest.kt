package com.josealonso.service


import com.josealonso.entity.OrderDTO
import com.josealonso.entity.OrderStatus
import com.josealonso.entity.UserDTO
import com.josealonso.extensions.fromDTO
import com.josealonso.repository.OrderRepository
import io.mockk.*
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal
import kotlin.test.assertNotNull

class OrderServiceTest {

    private lateinit var orderRepository: OrderRepository
    private lateinit var orderService: OrderServiceImpl

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
    fun setUp() {
        orderRepository = mockk()
        orderService = OrderServiceImpl(orderRepository)
    }

    @Test
    fun `createOrder should save and return the created order`() {
        every { orderRepository.save(any()) } returns orderDTOExample.fromDTO()

        val result = orderService.createOrder(orderDTOExample)

        assertNotNull(result)
        assertEquals(orderDTOExample, result)
        verify(exactly = 1) { orderRepository.save(any()) }
    }

    @Test
    fun `getOrderById should return the order when it exists`() {
        val orderId = orderDTOExample.orderId
        val orderDTO = orderDTOExample
        // every { orderRepository.findById(orderId) } returns Optional.of(orderDTOExample.fromDTO())
        every { orderRepository.findByIdOrNull(orderId) } returns orderDTOExample.fromDTO()  // Both lines are equivalent

        val result = orderService.getOrderById(orderId)

        assertNotNull(result)
        assertEquals(orderDTO, result)
        verify(exactly = 1) { orderRepository.findById(orderId) }
    }

        @Test
        fun `getAllOrders should return all orders`() {
            val orders = listOf(
                orderDTOExample, orderDTOExample2
            )
            every { orderRepository.findAll() } returns orders.map { it.fromDTO() }

            val result = orderService.getAllOrders()

            assertEquals(2, result.size)
            assertEquals(orders, result)
            verify(exactly = 1) { orderRepository.findAll() }
        }

        @Test
        fun `updateOrder should update and return the order`() {
            val orderId = orderDTOExample.orderId
            val updatedOrderDTO = orderDTOExample
            every { orderRepository.findByIdOrNull(orderId) } returns updatedOrderDTO.fromDTO()
            every { orderRepository.save(any()) } returns updatedOrderDTO.fromDTO()

            val result = orderService.updateOrder(orderId, updatedOrderDTO)

            assertNotNull(result)
            assertEquals(updatedOrderDTO, result)
            verify(exactly = 1) { orderRepository.findByIdOrNull(orderId) }
            verify(exactly = 1) { orderRepository.save(any()) }
        }

        @Test
        fun `updateOrderStatus should update and return the order with new status`() {
            val orderId = orderDTOExample.orderId
            val newStatus = OrderStatus.COMPLETED
            val updatedOrderDTO = orderDTOExample.copy()
            updatedOrderDTO.status = newStatus

            every { orderRepository.findByIdOrNull(orderId) } returns orderDTOExample.fromDTO()
            every { orderRepository.save(any()) } returns updatedOrderDTO.fromDTO()

            val result = orderService.updateOrderStatus(orderId, newStatus)

            assertNotNull(result)
            assertEquals(updatedOrderDTO, result)
            assertEquals(newStatus, result.status)
            verify(exactly = 1) { orderRepository.findByIdOrNull(orderId) }
            verify(exactly = 1) { orderRepository.save(any()) }
        }

    @Test
    fun `deleteOrder should delete the order`() {
        val orderId = 1L
        every { orderRepository.existsById(orderId) } returns true
        every { orderRepository.deleteById(orderId) } just Runs

        orderService.deleteOrder(orderId)

        verify(exactly = 1) { orderRepository.deleteById(orderId) }
    }

}
