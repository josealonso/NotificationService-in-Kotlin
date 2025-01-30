package com.josealonso.consumer.entity


import java.math.BigDecimal

data class OrderDTO(
    val orderId: Long,
    val name: String,
    val price: BigDecimal,
    val notificationType: NotificationType,
    var status: OrderStatus = OrderStatus.PENDING,
    val userId: UserDTO
)

data class UserDTO(
    val userId: Long,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val orders: MutableList<OrderDTO> = mutableListOf()
)

enum class NotificationType {
    EMAIL, SMS
}

enum class OrderStatus {
    PENDING,
    PROCESSED,
    COMPLETED,
    CANCELLED
}