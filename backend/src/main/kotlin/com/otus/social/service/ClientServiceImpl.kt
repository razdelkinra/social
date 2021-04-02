package com.otus.social.service

import arrow.core.Either
import com.otus.social.dto.UserDto
import com.otus.social.model.Failure
import com.otus.social.model.UserNotFound
import com.otus.social.repository.ClientRepository
import com.otus.social.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class ClientServiceImpl(
        val clientRepository: ClientRepository,
        val userRepository: UserRepository) : ClientService {

    @Transactional
    override fun getToken(username: String, password: String): Either<Failure, String> {
        clientRepository.getClient(username, password)?.let { client ->
            userRepository.getUser(client.userId)?.let {
                val token = generateToken(it)
                client.token = token
                clientRepository.addClient(client)
                return Either.right(token)
            }
        }
        return Either.left(UserNotFound(USER_NOT_FOUND))
    }

    override fun findByToken(token: String): UserDetails? {
        clientRepository.findByToken(token)?.let { client ->
            return User(client.login, client.password, true, true, true, true, AuthorityUtils.createAuthorityList("USER"))
        }
        throw UserNotFound(USER_NOT_FOUND)
    }

    private fun generateToken(user: UserDto): String {
        val tokenData = HashMap<String, Any>()
        tokenData["clientType"] = "user"
        tokenData["userId"] = user.id.toString()
        tokenData["firstName"] = user.firstName
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