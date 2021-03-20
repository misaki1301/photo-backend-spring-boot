package com.shibuyaxpress.photobackend.models

import org.springframework.data.annotation.Id

data class Order(
    @Id
    var id: String? = null
)