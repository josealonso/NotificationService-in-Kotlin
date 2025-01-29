package com.josealonso.exceptions

import com.josealonso.consumer.OrderNotification

class UnknownOrderException(orderNotification: OrderNotification) :
    IllegalArgumentException("Unknown order status: ${orderNotification.status}")

