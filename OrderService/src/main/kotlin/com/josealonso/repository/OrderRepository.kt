package com.josealonso.com.josealonso.repository

import com.josealonso.com.josealonso.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>
