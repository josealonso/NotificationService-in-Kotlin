package com.josealonso.entity

data class UserDTO(
    val userId: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val orders: MutableList<Order> = mutableListOf(),
    )