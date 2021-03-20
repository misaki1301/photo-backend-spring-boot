package com.shibuyaxpress.photobackend.controllers

import com.shibuyaxpress.photobackend.security.JwtTokenUtil
import com.shibuyaxpress.photobackend.services.JwUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
class AuthenticationController {
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var userDetailsService: JwUserDetailsService


    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authRequest: JwtRequest): ResponseEntity<Any> {
        authenticate(authRequest.username, authRequest.password)

        val userDetails = userDetailsService.loadUserByUsername(authRequest.username)

        val token = jwtTokenUtil.generateToken(userDetails)

        return  ResponseEntity.ok(JwResponse(jwttoken = token))
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

}

data class JwtRequest(
    var username: String,
    var password: String
)

data class JwResponse(
    var serialVersionUID: Long = -8091879091924046844L,
    var jwttoken: String
)