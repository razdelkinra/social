package com.otus.social.repository

import com.otus.social.dto.ClientDto
import com.otus.social.mapper.ClientMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ClientRepository(
        var jdbcTemplate: JdbcTemplate,
        var namedJdbcTemplate: NamedParameterJdbcTemplate,
        val passwordEncoder: PasswordEncoder) {

    val SELECT_CLIENT_QUERY = "SELECT * FROM CLIENTS WHERE login = :login"
    val SELECT_CLIENT_BY_TOKEN_QUERY = "SELECT * FROM CLIENTS WHERE token = :token"
    val SELECT_CLIENT_BY_LOGIN_QUERY = "SELECT * FROM CLIENTS WHERE login = :login"
    val SELECT_CLIENT_BY_USER_ID_QUERY = "SELECT * FROM CLIENTS WHERE user_id = :userId"
    val INSERT_CLIENT_QUERY = "INSERT INTO CLIENTS VALUES (?, ?, ?, ?)"
    val UPDATE_CLIENT_QUERY =
            "UPDATE CLIENTS SET user_id = :userId, login = :login, password = :password, token = :token where user_id = :userId"

    fun getClient(login: String, password: String): ClientDto? {
        val namedParameters = MapSqlParameterSource().
        addValue("login", login)
        val listResult = namedJdbcTemplate.query(SELECT_CLIENT_QUERY, namedParameters, ClientMapper())
        return if (listResult.isEmpty() || !passwordEncoder.matches(password, listResult[0].password)) null else listResult[0]
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

    fun addClient(clientDto: ClientDto): Int {
        findByLogin(clientDto.login)?.let { client ->
            val params = getClientMapParam(clientDto).addValue("userId", clientDto.userId)
            return namedJdbcTemplate.update(UPDATE_CLIENT_QUERY, params)
        }
        return jdbcTemplate.update(INSERT_CLIENT_QUERY, clientDto.userId, clientDto.login, clientDto.password, clientDto.token)
    }

    private fun getClientMapParam(client: ClientDto): MapSqlParameterSource {
        return MapSqlParameterSource()
                .addValue("userId", client.userId)
                .addValue("login", client.login)
                .addValue("password", client.password)
                .addValue("token", client.token)
    }
}