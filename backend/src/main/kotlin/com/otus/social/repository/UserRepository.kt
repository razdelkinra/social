package com.otus.social.repository

import com.otus.social.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findAllByLastName(lastName: String): List<User>
    fun findAllByFirstName(firstName: String): List<User>
}