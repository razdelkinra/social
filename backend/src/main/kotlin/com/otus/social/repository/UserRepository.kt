package com.otus.social.repository

import com.otus.social.dto.ClientDto
import com.otus.social.dto.UserDto
import com.otus.social.mapper.UserMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class UserRepository(
        val jdbcTemplate: JdbcTemplate,
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
    val INSERT_FRIEND_QUERY = "INSERT INTO FRIENDS VALUES (?, ?)"
    val SELECT_FRIENDS_QUERY = """
        SELECT * FROM USERS WHERE USER_ID IN (
          SELECT friend_id FROM FRIENDS WHERE user_id = :id
        )
    """.trimIndent()

    fun saveUser(user: UserDto): Long {
        val params = getUserMapParam(user)
        val id = jdbcInsert.executeAndReturnKey(params)
        id.toLong().let {
            val clientDto = ClientDto(it, user.credential!!.login, user.credential.password, null)
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

    fun getUserByLogin(login: String): UserDto? {
        return clientRepository.findByLogin(login)?.let {
            val namedParameters = MapSqlParameterSource().addValue("id", it.userId)
            val listResult = namedJdbcTemplate.query(SELECT_USER_QUERY, namedParameters, UserMapper())
            return if (listResult.isEmpty()) null else listResult[0]
        }
    }

    fun addFriend(userId: Long, friendId: Long): Boolean {
        jdbcTemplate.update(INSERT_FRIEND_QUERY, userId, friendId)
        jdbcTemplate.update(INSERT_FRIEND_QUERY, friendId, userId)
        return true
    }

    fun getFriend(id: Long): List<UserDto> {
        val namedParameters = MapSqlParameterSource().addValue("id", id);
        return namedJdbcTemplate.query(SELECT_FRIENDS_QUERY, namedParameters, UserMapper());
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
