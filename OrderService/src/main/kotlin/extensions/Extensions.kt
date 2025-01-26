package com.josealonso.extensions

import com.josealonso.entity.Order
import com.josealonso.entity.OrderDTO
import com.josealonso.entity.User
import com.josealonso.entity.UserDTO
import java.time.LocalDateTime

fun OrderDTO.toDTO(order: Order) =
    OrderDTO(orderId = order.orderId,
        name = order.name,
        price = order.price,
        status = order.status,
        userId = order.userId)

fun Order.fromDTO(orderDTO: OrderDTO) =
    Order(
        id = -1,
        orderId = orderDTO.orderId,
        name = orderDTO.name,
        price = orderDTO.price,
        status = orderDTO.status,
        userId = orderDTO.userId,
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
