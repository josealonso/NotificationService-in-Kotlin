package com.josealonso.com.josealonso.exceptions

class UserNotFoundException(id: Long) : RuntimeException("Order not found with id: $id")