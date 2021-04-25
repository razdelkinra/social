package com.otus.social.mapper

import com.otus.social.dto.request.UserDto
import com.otus.social.model.Gender
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class UserMapper : RowMapper<UserDto> {

    override fun mapRow(rs: ResultSet, rowNum: Int): UserDto? {
        return UserDto(
                rs.getLong("user_id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getDate("birthday").toLocalDate(),
                Gender.valueOf(rs.getString("gender").toUpperCase()),
                rs.getString("city"),
                rs.getString("interests"))
    }
}