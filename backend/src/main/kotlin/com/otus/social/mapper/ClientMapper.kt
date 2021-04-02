package com.otus.social.mapper

import com.otus.social.dto.ClientDto
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class ClientMapper : RowMapper<ClientDto> {

    override fun mapRow(rs: ResultSet, rowNum: Int): ClientDto? {
        return ClientDto(
                rs.getLong("user_id"),
                rs.getString("login"),
                rs.getString("password"),
                rs.getString("token"))
    }
}