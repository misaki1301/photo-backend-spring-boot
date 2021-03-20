package com.shibuyaxpress.photobackend.security

import com.shibuyaxpress.photobackend.models.User
import org.springframework.beans.factory.annotation.Value
import java.io.Serializable
import java.util.function.Function
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.security.auth.Subject
import kotlin.collections.HashMap
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Component
class JwtTokenUtil: Serializable {

    val serialVersionUID: Long = -2550185165626007488L
    val JWT_TOKEN_VALIDITY: Long = 5 * 60 * 60

    @Value("\${jwt.secret}")
    lateinit var secret: String

    //retrieve user from jwt token
    fun getUserFromToken(token: String): String = getClaimFromToken(token) { obj :Claims? -> obj!!.subject }

    //retrieve expiration date from jwt token
    fun getExpirationDateFromToken(token: String?): Date? {
        return getClaimFromToken(token) { obj: Claims? -> obj!!.expiration }
    }

    //gets claims from the token
    fun <T> getClaimFromToken(token: String?, claimsResolver: (Claims?) -> T): T {
        val claims: Claims = getAllClaimsFromToken(token)
        return claimsResolver.invoke(claims)
    }

    private fun getAllClaimsFromToken(token: String?): Claims
        = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body

    //get expired Token
    private fun isTokenExpired(token: String): Boolean {
        val expirationDate = getExpirationDateFromToken(token)
        return expirationDate!!.before(Date())
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims = HashMap<String, Any>()
        return doGenerateToken(claims, userDetails.username!!)
    }

    //while creating the token -
//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
//2. Sign the JWT using the HS512 algorithm and secret key.
//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
//   compaction of the JWT to a URL-safe string
    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, secret).compact()
    }

    //validate Token
    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUserFromToken(token)
        return (username == userDetails.username) && !isTokenExpired(token)
    }
}