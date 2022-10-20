package com.otus.social.service

import com.otus.social.dto.request.FriendApplication
import com.otus.social.entity.FriendRequest
import com.otus.social.model.UserNotFound
import com.otus.social.repository.FriendRequestRepository
import com.otus.social.service.ClientService.Companion.USER_NOT_FOUND
import org.springframework.stereotype.Service

@Service
class FriendRequestService(
        private var friendRequestRepository: FriendRequestRepository,
        private val userService: UserService
) {

    fun addFriendRequest(userId: Long, friendId: Long): FriendRequest {
        if (friendId == 0L) throw UserNotFound(USER_NOT_FOUND)
        return friendRequestRepository.save(FriendRequest(null, userId, friendId))
    }

    fun getFriendRequest(userId: Long): MutableList<FriendApplication> {
        val listApplication = mutableListOf<FriendApplication>()
        friendRequestRepository.getAllByUserId(userId).forEach { request ->
            userService.getUser(request.friendId).orNull()?.let { friend ->
                val friendApplication = FriendApplication(friend.id, friend.firstName!!, friend.lastName!!, "")
                listApplication.add(friendApplication)
            }
        }
        return listApplication
    }

}