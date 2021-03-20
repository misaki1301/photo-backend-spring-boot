package com.shibuyaxpress.photobackend.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
    @Id
    var id: String? = null,
    var username: String? = null,
    var password: String? = null,
    var email: String? = null
)

@Document(collection = "people")
data class Person(
    @Id
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null
)
