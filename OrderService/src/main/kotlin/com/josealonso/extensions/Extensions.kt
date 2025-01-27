package com.josealonso.com.josealonso.extensions

import com.josealonso.com.josealonso.entity.Order
import com.josealonso.com.josealonso.entity.OrderDTO
import com.josealonso.com.josealonso.entity.User
import com.josealonso.com.josealonso.entity.UserDTO
import java.time.LocalDateTime

fun User.toDTO() =
    UserDTO(
        userId = this.userId,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        orders = this.orders,
    )

fun UserDTO.fromDTO() =
    User(
        id = -1,
        userId = this.userId,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        orders = this.orders,
        createdAt = LocalDateTime.now(),
        modifiedAt = LocalDateTime.now(),
    )

fun Order.toDTO() =
    OrderDTO(orderId = this.orderId,
        name = this.name,
        price = this.price,
        status = this.status,
        userId = this.userId.toDTO())

fun Order.copy() =
    Order(
        id = id,
        orderId = this.orderId,
        name = this.name,
        price = this.price,
        status = this.status,
        userId = this.userId,
        modifiedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
    )

fun OrderDTO.fromDTO() =
    Order(
        id = -1,
        orderId = this.orderId,
        name = this.name,
        price = this.price,
        status = this.status,
        userId = this.userId.fromDTO(),
        modifiedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
    )

