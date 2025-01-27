package com.josealonso.com.josealonso.exceptions

class OrderNotFoundException(id: Long) : RuntimeException("Order not found with id: $id")
