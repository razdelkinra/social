package com.otus.social.repository

import com.otus.social.entity.Client
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : CrudRepository<Client, Long> {
    fun findByLoginAndPassword(login: String, password: String) : Client?
    fun findByToken(token: String) : Client?
    fun findByLogin(login: String) : Client?
}