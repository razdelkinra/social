package com.otus.social.repository

import com.otus.social.entity.FriendRequest
import com.otus.social.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FriendRequestRepository : CrudRepository<FriendRequest, Long> {
    fun getAllByUserId(userId: Long) : List<FriendRequest>
}