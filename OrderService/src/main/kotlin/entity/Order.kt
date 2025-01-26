package com.josealonso.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,

    @Column(name = "order_id", nullable = false)
    val orderId: Long,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "price", nullable = false)
    val price: BigDecimal,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus = OrderStatus.PENDING,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val userId: User,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "modified_at", nullable = false)
    val modifiedAt: LocalDateTime = LocalDateTime.now(),
)

enum class OrderStatus {
    PENDING, PROCESSING, COMPLETED, CANCELLED
}


/*
Avoid data classes, because Most JPA entities contain
at least one generated value for equals and hashCode — for example, auto-generated identifiers.
 */