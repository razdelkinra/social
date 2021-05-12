package com.otus.social.service

import arrow.core.Either
import com.otus.social.dto.request.FilterUserDto
import com.otus.social.dto.request.UserDto
import com.otus.social.model.InvalidRequest
import com.otus.social.model.UserAlreadyExist
import com.otus.social.repository.ClientRepository
import com.otus.social.repository.UserRepository
import com.otus.social.utils.DataGenerator
import com.otus.social.utils.unsafeCatch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
        private val userRepository: UserRepository,
        private val clientRepository: ClientRepository,
        private val dataGenerator: DataGenerator) {

    @Transactional
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

    fun getUser(id: Long) = Either.unsafeCatch { userRepository.getUser(id) }

    fun getUsers(filter: FilterUserDto, id: Long) = Either.unsafeCatch { userRepository.getUsers(filter).filter { it.id != id } }

    fun getUsers(id: Long) = Either.unsafeCatch { userRepository.getUsers().filterNot { user -> user.id == id } }

    fun getUserByLogin(login: String) = Either.unsafeCatch { userRepository.getUserByLogin(login) }

    fun generateUser(count: Int) = Either.unsafeCatch { dataGenerator.generateUser(count) }

}