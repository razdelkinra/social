package com.otus.social.repository

import com.otus.social.dto.ClientDto
import com.otus.social.dto.request.FilterUserDto
import com.otus.social.dto.request.UserDto
import com.otus.social.mapper.UserMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
@Transactional
class UserRepository(
        val jdbcTemplate: JdbcTemplate,
        val passwordEncoder: PasswordEncoder,
        val namedJdbcTemplate: NamedParameterJdbcTemplate,
        val clientRepository: ClientRepository) {

    val simpleJdbcInsert = SimpleJdbcInsert(jdbcTemplate)

    val jdbcInsert: SimpleJdbcInsert by lazy {
        simpleJdbcInsert.withTableName("USERS").usingGeneratedKeyColumns("USER_ID")
        simpleJdbcInsert
    }

    val UPDATE_USER_QUERY =
            "UPDATE USERS SET firstname = :firstname, lastName = :lastName, birthday = :birthday, " +
                    "gender = :gender, interests = :interests, city = :city where user_id = :userId"
    val SELECT_USER_QUERY = "SELECT * FROM USERS WHERE USER_ID = :id"
    val SELECT_ALL_USER_QUERY = "SELECT * FROM USERS"
    val SELECT_USER_BY_NAME_QUERY = "SELECT * FROM USERS WHERE firstname LIKE :firstName AND lastName LIKE :lastName"


    fun saveUser(user: UserDto): Long {
        val params = getUserMapParam(user)
        val id = jdbcInsert.executeAndReturnKey(params)
        id.toLong().let {
            val password = passwordEncoder.encode(user.credential?.password)
            val clientDto = ClientDto(it, user.credential!!.login, password, null)
            clientRepository.addClient(clientDto)
            return it
        }
    }

    fun updateUser(user: UserDto): Long {
        val params = getUserMapParam(user).addValue("userId", user.id)
        namedJdbcTemplate.update(UPDATE_USER_QUERY, params)
        return user.id!!
    }

    fun getUser(id: Long): UserDto? {
        val namedParameters = MapSqlParameterSource().addValue("id", id)
        val listResult = namedJdbcTemplate.query(SELECT_USER_QUERY, namedParameters, UserMapper())
        return if (listResult.isEmpty()) null else listResult[0]
    }

    fun getUsers(filter: FilterUserDto): List<UserDto> {
        val namedParameters = MapSqlParameterSource().
        addValue("firstName", filter.firstName + "%").
        addValue("lastName", filter.lastName + "%")
        val listResult = namedJdbcTemplate.query(SELECT_USER_BY_NAME_QUERY, namedParameters, UserMapper())
        return if (listResult.isEmpty()) arrayListOf() else listResult
    }

    fun getUsers(): MutableList<UserDto> = namedJdbcTemplate.query(SELECT_ALL_USER_QUERY, UserMapper())

    fun getUserByLogin(login: String): UserDto? {
        return clientRepository.findByLogin(login)?.let {
            val namedParameters = MapSqlParameterSource().addValue("id", it.userId)
            val listResult = namedJdbcTemplate.query(SELECT_USER_QUERY, namedParameters, UserMapper())
            return if (listResult.isEmpty()) null else listResult[0]
        }
    }

    private fun getUserMapParam(user: UserDto): MapSqlParameterSource {
        return MapSqlParameterSource()
                .addValue("firstname", user.firstName)
                .addValue("lastName", user.lastName)
                .addValue("birthday", user.birthDay)
                .addValue("gender", user.gender.name.toLowerCase())
                .addValue("interests", user.interests)
                .addValue("city", user.city)
    }
}