package com.otus.social.service

import arrow.core.Either
import com.otus.social.dto.request.FilterUserDto
import com.otus.social.dto.request.UserDto
import com.otus.social.entity.Client
import com.otus.social.entity.User
import com.otus.social.model.InvalidRequest
import com.otus.social.model.UserAlreadyExist
import com.otus.social.repository.UserRepository
import com.otus.social.utils.DataGenerator
import com.otus.social.utils.unsafeCatch
import javassist.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
        private val userRepository: UserRepository,
        private val clientService: ClientService,
        private val dataGenerator: DataGenerator
) {

    @Transactional
    fun saveUser(user: UserDto): Long {
        val login = user.credential!!.login
        val client = clientService.findByLogin(login)
        val user1 = User(null, user.firstName, user.lastName, user.birthDay, user.gender.name, user.city, user.interests)
        if (client != null) {
            throw UserAlreadyExist("User with login/email $login already exist")
        }
        val userId = userRepository.save(user1).id!!
        val password = user.credential.password
        return clientService.save(Client(null, userId, user.credential.login, password, null))
    }


    fun updateUser(login: String, user: UserDto): Long {
        val entity = User(user.id, user.firstName, user.lastName, user.birthDay, user.gender.name, user.city, user.interests)
        val client = clientService.findByLogin(login)
        if (client?.userId != user.id) {
            throw InvalidRequest("Not permitted operation");
        }

        return userRepository.save(entity).id!!
    }

    fun getUser(id: Long): User? {
        return userRepository.findByIdOrNull(id)
    }

    fun getUsers(filter: FilterUserDto, id: Long): Either<Throwable, List<User>> = Either.unsafeCatch {
        if (filter.firstName != null) {
            userRepository.findAllByFirstName(filter.firstName).filter { it.id != id }
        } else {
            userRepository.findAllByLastName(filter.lastName!!).filter { it.id != id }
        }
    }

    fun getUsers(id: Long): List<User> {
        return userRepository.findAll().filterNot { user -> user.id == id }
    }

    fun getUserByLogin(login: String): User {
        clientService.findByLogin(login)?.let {
            return userRepository.findById(it.userId).get()
        }
        throw NotFoundException("")
    }

    fun generateUser(count: Int) = Either.unsafeCatch { dataGenerator.generateUser(count) }

}