package com.shibuyaxpress.photobackend.models

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.boot.context.properties.bind.DefaultValue
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Reference
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "products")
data class Product(
    @Id
    var id: String? = null,
    var name: String? = null,
    @DefaultValue("")
    var description: String? = "",
    var price: Double? = null,
    var image: String? = null,
    var thumbnail: String? = null,
    @Reference
    var type: ProductType,
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var updatedAt: LocalDateTime? = null
)
