package com.otus.social.mapper

import com.otus.social.dto.request.FriendRequestDto
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class FriendMapper : RowMapper<FriendRequestDto> {

    override fun mapRow(rs: ResultSet, rowNum: Int): FriendRequestDto? {
        return FriendRequestDto(
                rs.getLong("user_id"),
                rs.getLong("from_user_id"),
                rs.getString("message"),
                rs.getDate("request_date").toLocalDate())
    }
}