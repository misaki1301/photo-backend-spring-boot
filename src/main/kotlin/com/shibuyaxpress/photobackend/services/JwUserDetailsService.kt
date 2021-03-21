package com.shibuyaxpress.photobackend.services

import com.shibuyaxpress.photobackend.UserRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.security.SecureRandom
import kotlin.Exception

@Service
class JwUserDetailsService:  UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
            val user = userRepository.findUserByUsername(username)
                ?: throw UsernameNotFoundException("User not found with username: $username")

                return with(user) {
                    User.withUsername(username)
                        .password(password)
                        .authorities("ADMIN")
                        .build()
                }
    }
}