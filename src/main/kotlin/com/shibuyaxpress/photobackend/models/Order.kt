package com.shibuyaxpress.photobackend.models

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Reference
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "orders")
data class Order(
    @Id
    var id: String? = null,
    @Reference
    var receptor: User,
    @Reference
    var sender: User,
    var location: String,
    var description: String,
    var type: String,
    var quantity: Int = 0,
    @Reference
    var product: List<Product>,
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var updatedAt: LocalDateTime? = null,
    var deletedAt: LocalDateTime? = null,
    var status: String = "PENDING",
    var isActive: Boolean = true
)