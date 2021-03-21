package com.shibuyaxpress.photobackend.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

@Document(collection = "users")
data class User(
    @Id
    var id: String? = null,
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var person: Person?
)

@Document(collection = "people")
data class Person(
    @Id
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var profileImage: String? = null
)
