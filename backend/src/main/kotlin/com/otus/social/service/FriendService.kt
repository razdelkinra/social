package com.otus.social.service

import arrow.core.Either
import com.otus.social.entity.Friend
import com.otus.social.model.UserNotFound
import com.otus.social.repository.FriendRepository
import com.otus.social.utils.unsafeCatch
import org.springframework.stereotype.Service

@Service
class FriendService(
        private var friendRepository: FriendRepository,
        private var friendRequestService: FriendRequestService
) {

    fun addFriend(userId: Long, friendId: Long): Friend {
        if (friendId == 0L) throw UserNotFound("")
        friendRequestService.deleteFriendRequest(userId, friendId)
        return friendRepository.save(Friend(null, userId, friendId))
    }

    fun getFriends(userId: Long) = Either.unsafeCatch { friendRepository.getAllByUserId(userId) }

}