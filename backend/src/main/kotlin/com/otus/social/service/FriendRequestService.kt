package com.otus.social.service

import arrow.core.Either
import com.otus.social.dto.request.FriendApplication
import com.otus.social.entity.FriendRequest
import com.otus.social.model.UserNotFound
import com.otus.social.repository.FriendRequestRepository
import com.otus.social.service.ClientService.Companion.USER_NOT_FOUND
import com.otus.social.utils.unsafeCatch
import org.springframework.stereotype.Service

@Service
class FriendRequestService(
        private var friendRequestRepository: FriendRequestRepository,
        private val userService: UserService
) {

    fun addFriendRequest(userId: Long, friendId: Long): FriendRequest {
        if (friendId == 0L) throw UserNotFound(USER_NOT_FOUND)
        return friendRequestRepository.save(FriendRequest(userId = userId, friendId = friendId))
    }

    fun getFriendRequest(userId: Long): MutableList<FriendApplication> {
        val listApplication = mutableListOf<FriendApplication>()
        friendRequestRepository.getAllByUserId(userId).forEach { request ->
            userService.getUser(request.friendId)?.let { friend ->
                val friendApplication = FriendApplication(friend.id, friend.firstName, friend.lastName, "")
                listApplication.add(friendApplication)
            }
        }
        return listApplication
    }

    fun deleteFriendRequest(userId: Long, fromUserId: Long) = Either.unsafeCatch {
        friendRequestRepository.delete(FriendRequest(null, userId, fromUserId))
    }

}