package com.josealonso.com.josealonso.entity

data class UserDTO(
    val userId: Long,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val orders: MutableList<Order> = mutableListOf(),
    )