package com.shibuyaxpress.photobackend.models

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Reference
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "variants")
data class Variant(
    @Id
    var id: String? = null,
    var price: Double = 0.0,
    var stock: Int = 0,
    var image: String? = null,
    var thumbnail: String? = null,
    @Reference
    var size: Size,
    @Reference
    var color: Color,
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var updatedAt: LocalDateTime? = null,
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var deletedAt: LocalDateTime? = null,
    var isActive: Boolean = true
)