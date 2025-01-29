package com.josealonso.entity

import java.math.BigDecimal

data class OrderDTO(
    val orderId: Long,
    val name: String,
    val price: BigDecimal,
    var status: OrderStatus = OrderStatus.PENDING,
    val userId: UserDTO
)


