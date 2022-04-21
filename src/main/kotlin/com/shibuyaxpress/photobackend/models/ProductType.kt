package com.shibuyaxpress.photobackend.models

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "product_type")
data class ProductType(
    @Id
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var updatedAt: LocalDateTime? = null,
    var deletedAt: LocalDateTime? = null,
    var isActive: Boolean = true
)