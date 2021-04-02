package com.otus.social.repository

import com.otus.social.dto.ClientDto
import com.otus.social.mapper.ClientMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ClientRepository(var jdbcTemplate: JdbcTemplate, var namedJdbcTemplate: NamedParameterJdbcTemplate) {

    val SELECT_CLIENT_QUERY = "SELECT * FROM CLIENTS WHERE login = :login and password = :password"
    val SELECT_CLIENT_BY_TOKEN_QUERY = "SELECT * FROM CLIENTS WHERE token = :token"
    val SELECT_CLIENT_BY_LOGIN_QUERY = "SELECT * FROM CLIENTS WHERE login = :login"
    val SELECT_CLIENT_BY_USER_ID_QUERY = "SELECT * FROM CLIENTS WHERE user_id = :userId"
    val INSERT_CLIENT_QUERY = "INSERT INTO CLIENTS VALUES (?, ?, ?, ?)"

    fun getClient(login: String, password: String): ClientDto? {
        val namedParameters = MapSqlParameterSource().addValue("login", login).addValue("password", password)
        val listResult = namedJdbcTemplate.query(SELECT_CLIENT_QUERY, namedParameters, ClientMapper())
        return if (listResult.isEmpty()) null else listResult[0]
    }

    fun findByToken(token: String): ClientDto? {
        val namedParameters = MapSqlParameterSource().addValue("token", token)
        val listResult = namedJdbcTemplate.query(SELECT_CLIENT_BY_TOKEN_QUERY, namedParameters, ClientMapper())
        return if (listResult.isEmpty()) null else listResult[0]
    }

    fun findByLogin(login: String): ClientDto? {
        val namedParameters = MapSqlParameterSource().addValue("login", login)
        val listResult = namedJdbcTemplate.query(SELECT_CLIENT_BY_LOGIN_QUERY, namedParameters, ClientMapper())
        return if (listResult.isEmpty()) null else listResult[0]
    }

    fun addClient(credential: ClientDto): Int {
        return jdbcTemplate.update(INSERT_CLIENT_QUERY, credential.userId, credential.login, credential.password, credential.token)
    }

    fun getUserByUserId(userId: Long): ClientDto? {
        val namedParameters = MapSqlParameterSource().addValue("userId", userId);
        val listResult = namedJdbcTemplate.query(SELECT_CLIENT_BY_USER_ID_QUERY, namedParameters, ClientMapper())
        return if (listResult.isEmpty()) null else listResult[0]
    }

}