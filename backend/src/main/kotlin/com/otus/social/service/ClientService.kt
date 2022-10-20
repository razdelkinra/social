package com.otus.social.service

import com.otus.social.entity.Client
import com.otus.social.entity.User
import com.otus.social.model.SocialUserDetails
import com.otus.social.model.UserNotFound
import com.otus.social.repository.ClientRepository
import com.otus.social.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class ClientService(
        val clientRepository: ClientRepository,
        val userRepository: UserRepository
) {

    @Transactional
    fun getToken(username: String, password: String): String {
        clientRepository.findByLoginAndPassword(username, password)?.let { client ->
            userRepository.findById(client.userId).get().let {
                val token = generateToken(it)
                clientRepository.save(Client(null, client.userId, client.login, client.password, token))
                return token
            }
        }
        throw UserNotFound(USER_NOT_FOUND)
    }

    fun findByToken(token: String): UserDetails? {
        clientRepository.findByToken(token)?.let { client ->
            val user = SocialUserDetails(client.login, client.password, true, true, true, true, AuthorityUtils.createAuthorityList("USER"))
            user.id = client.userId
            return user
        }
        throw UserNotFound(USER_NOT_FOUND)
    }

    fun findByLogin(login: String): Client? {
        return clientRepository.findByLogin(login)
    }

    fun save(client: Client): Long {
        return clientRepository.save(client).id!!
    }

    private fun generateToken(user: User): String {
        val tokenData = HashMap<String, Any>()
        tokenData["clientType"] = "user"
        tokenData["userId"] = user.id.toString()
        tokenData["firstName"] = user.firstName!!
        tokenData["token_create_date"] = LocalDateTime.now()
        val expirationDate = LocalDateTime.now().plusHours(2)
        tokenData["token_expiration_date"] = expirationDate
        val jwtBuilder = Jwts.builder()
        jwtBuilder.setExpiration(java.sql.Date.valueOf(expirationDate.toLocalDate()))
        jwtBuilder.setClaims(tokenData)
        return jwtBuilder.signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256)).compact()
    }

    companion object {
        const val USER_NOT_FOUND = "User not found"
    }
}