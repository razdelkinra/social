package com.otus.social.repository

import com.otus.social.entity.Friend
import com.otus.social.entity.FriendRequest
import com.otus.social.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FriendRepository : CrudRepository<Friend, Long> {
    fun getAllByUserId(userId: Long) : List<Friend>
}