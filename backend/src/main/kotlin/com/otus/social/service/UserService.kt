package com.otus.social.service

import arrow.core.Either
import com.otus.social.dto.UserDto
import com.otus.social.model.InvalidRequest
import com.otus.social.model.UserAlreadyExist
import com.otus.social.repository.ClientRepository
import com.otus.social.repository.UserRepository
import com.otus.social.utils.unsafeCatch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
        private val userRepository: UserRepository,
        private val clientRepository: ClientRepository) {

    //@Transactional
    fun addUser(user: UserDto): Either<Throwable, Long> {
        return Either.unsafeCatch {
            val login = user.credential!!.login
            val client = clientRepository.findByLogin(login)
            if (client != null) {
                throw UserAlreadyExist("User with login/email $login already exist")
            }
            userRepository.saveUser(user)
        }
    }

    fun updateUser(login: String, user: UserDto): Either<Throwable, Long> {
        clientRepository.findByLogin(login).let {
            if (it?.userId != user.id) {
                return Either.left(InvalidRequest("Not permitted operation"))
            }
        }
        return Either.unsafeCatch { userRepository.updateUser(user) }
    }

    fun addFriend(userId: Long, friendId: Long) = Either.unsafeCatch { userRepository.addFriend(userId, friendId) }

    fun getFriends(userId: Long) = Either.unsafeCatch { userRepository.getFriend(userId) }

    fun getUser(id: Long) = Either.unsafeCatch { userRepository.getUser(id) }

    fun getUserByLogin(login: String) = Either.unsafeCatch { userRepository.getUserByLogin(login) }

}