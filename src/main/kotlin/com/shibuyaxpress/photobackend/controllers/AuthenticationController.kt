package com.shibuyaxpress.photobackend.controllers

import com.shibuyaxpress.photobackend.PersonRepository
import com.shibuyaxpress.photobackend.UserRepository
import com.shibuyaxpress.photobackend.models.User
import com.shibuyaxpress.photobackend.security.JwtTokenUtil
import com.shibuyaxpress.photobackend.services.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.security.SecureRandom

@RestController
@CrossOrigin(origins = ["*"])
class AuthenticationController(private val userRepository: UserRepository,
                               private val personRepository: PersonRepository) {
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var userDetailsService: JwtUserDetailsService


    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authRequest: JwtRequest): ResponseEntity<Any> {
        authenticate(authRequest.username, authRequest.password)

        val userDetails = userDetailsService.loadUserByUsername(authRequest.username)

        val user = userRepository.findUserByUsername(userDetails.username)

        val token = jwtTokenUtil.generateToken(userDetails)

        return  ResponseEntity.ok(JwResponse(jwttoken = token, user = user!!))
    }

    @GetMapping("/auth/user")
    fun getUserAuthenticated(): ResponseEntity<Any> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        try {
            return ResponseEntity.ok(UserResponse(userRepository.findUserByUsername(userDetails.username)!!))
        } catch (e: Exception) {
            return ResponseEntity.badRequest().build()
        }
    }

    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("User Disabled", e)
        } catch (e: BadCredentialsException) {
            throw Exception("Bad Credential", e)
        }
    }

    @PostMapping("/create/account")
     fun createAccount(@RequestBody user: User): ResponseEntity<Any> {
        val person = personRepository.save(user.person!!)
        val hasher = BCryptPasswordEncoder(10, SecureRandom())
        val userAux = User(username = user.username, password = hasher.encode(user.password), email = user.email, person = person)
        val finalUser = userRepository.save(userAux)
        return ResponseEntity.ok(finalUser)
    }

}

data class JwtRequest(
    var username: String,
    var password: String
)

data class JwResponse(
    var serialVersionUID: Long = -8091879091924046844L,
    var jwttoken: String,
    var user: User
)

data class UserResponse(
    var user: User
)