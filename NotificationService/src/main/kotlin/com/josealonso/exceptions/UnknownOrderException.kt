package com.josealonso.exceptions

import com.josealonso.listener.OrderNotification

class UnknownOrderException(orderNotification: OrderNotification) :
    IllegalArgumentException("Unknown order status: ${orderNotification.status}")

