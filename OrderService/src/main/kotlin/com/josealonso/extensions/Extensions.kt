package com.josealonso.com.josealonso.extensions

import com.josealonso.com.josealonso.entity.Order
import com.josealonso.com.josealonso.entity.OrderDTO
import com.josealonso.com.josealonso.entity.User
import com.josealonso.com.josealonso.entity.UserDTO
import java.time.LocalDateTime

fun Order.toDTO() =
    OrderDTO(orderId = this.orderId,
        name = this.name,
        price = this.price,
        status = this.status,
        userId = this.userId)

fun Order.copy() =
    Order(
        id = -1,
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
        userId = this.userId,
        modifiedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
    )

fun UserDTO.toDTO(user: User) =
    UserDTO(
        userId = user.userId,
        name = user.name,
        email = user.email,
        phoneNumber = user.phoneNumber,
        orders = user.orders,
    )

fun User.fromDTO(userDTO: UserDTO) =
    User(
        id = -1,
        userId = userDTO.userId,
        name = userDTO.name,
        email = userDTO.email,
        phoneNumber = userDTO.phoneNumber,
        orders = userDTO.orders,
        createdAt = LocalDateTime.now(),
        modifiedAt = LocalDateTime.now(),
    )
