package com.josealonso.exceptions

import com.josealonso.consumer.entity.OrderDTO

class UnknownOrderException(orderNotification: OrderDTO) :
    IllegalArgumentException("Unknown order status: ${orderNotification.status}")

