package com.shibuyaxpress.photobackend.security

import com.shibuyaxpress.photobackend.services.JwtUserDetailsService
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter: OncePerRequestFilter() {
    @Autowired
    private lateinit var jwtUserDetailsService: JwtUserDetailsService
    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestTokenHeader = request.getHeader("Authorization")

        var username: String? = null
        var token: String? = null

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.substring(7)
            try {
                username = jwtTokenUtil.getUserFromToken(token)
            } catch (e: IllegalArgumentException) {
                print("Unable to get JWT token")
            } catch (e: ExpiredJwtException) {
                print("Token Expired")
            }
        } else {
            logger.warn("JWT token doesnt begin with Bearer String")
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = this.jwtUserDetailsService.loadUserByUsername(username)

            if (jwtTokenUtil.validateToken(token!!, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        filterChain.doFilter(request, response)
    }

}