package com.shibuyaxpress.photobackend.models

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "colors")
data class Color(
    @Id
    var id: String? = null,
    var name: String? = null,
    var hex: String? = null,
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var updatedAt: LocalDateTime? = null,
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var deletedAt: LocalDateTime? = null,
    var isActive: Boolean = true,
    var isAvailable: Boolean = true,
    var isDefault: Boolean = false
)
