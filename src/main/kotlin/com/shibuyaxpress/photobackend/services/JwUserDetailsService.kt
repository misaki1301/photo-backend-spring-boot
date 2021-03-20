package com.shibuyaxpress.photobackend.services

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.security.SecureRandom

@Service
class JwUserDetailsService:  UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if ("misaki13" == username) {
            val bcryptPasswordEncoder = BCryptPasswordEncoder(10, SecureRandom())
            return User("misaki13", bcryptPasswordEncoder.encode("123123"), ArrayList())
        } else {
            throw UsernameNotFoundException("User not found with username: $username")
        }
    }
}