package com.shibuyaxpress.photobackend.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "firebase")
data class FirebaseConfigProperties(
    var bucketName: String = "",
    var imageUrl: String = ""
)
