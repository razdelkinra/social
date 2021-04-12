package com.otus.social.repository

import com.otus.social.dto.FriendRequestDto
import com.otus.social.dto.UserDto
import com.otus.social.mapper.FriendMapper
import com.otus.social.mapper.UserMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class FriendRepository(
        val jdbcTemplate: JdbcTemplate,
        val namedJdbcTemplate: NamedParameterJdbcTemplate) {

    val SELECT_FRIEND_REQUESTS_QUERY = "SELECT * FROM FRIEND_REQUEST WHERE USER_ID = :userId"
    val INSERT_FRIEND_REQUEST_QUERY = "INSERT INTO FRIEND_REQUEST VALUES (?, ?, ?, ?)"
    val INSERT_FRIEND_QUERY = "INSERT INTO FRIENDS VALUES (?, ?)"
    val SELECT_FRIENDS_QUERY = """
        SELECT * FROM USERS WHERE USER_ID IN (
          SELECT friend_id FROM FRIENDS WHERE user_id = :id
        )
    """.trimIndent()

    fun saveRequest(requestDto: FriendRequestDto) {
        jdbcTemplate.update(INSERT_FRIEND_REQUEST_QUERY, requestDto.userId, requestDto.fromUserId, requestDto.requestDate, requestDto.message)
    }

    fun getFriendRequest(userId: Long): MutableList<FriendRequestDto> {
        val namedParameters = MapSqlParameterSource().addValue("userId", userId)
        return namedJdbcTemplate.query(SELECT_FRIEND_REQUESTS_QUERY, namedParameters, FriendMapper())
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
}
