package com.otus.social.service

import arrow.core.Either
import com.otus.social.dto.FriendApplication
import com.otus.social.dto.FriendRequestDto
import com.otus.social.repository.FriendRepository
import com.otus.social.utils.unsafeCatch
import org.springframework.stereotype.Service

@Service
class FriendService(
        private val friendRepository: FriendRepository,
        private val userService: UserService
) {

    fun addFriend(userId: Long, friendId: Long) = Either.unsafeCatch {
        if (friendId == 0L) throw RuntimeException()
        deleteFriendRequest(userId, friendId)
        friendRepository.addFriend(userId, friendId)
    }

    fun getFriends(userId: Long) = Either.unsafeCatch { friendRepository.getFriend(userId) }

    fun getFriendRequest(userId: Long) = Either.unsafeCatch {
        val listApplication = mutableListOf<FriendApplication>()
        friendRepository.getFriendRequest(userId).forEach { request ->
            userService.getUser(request.fromUserId).orNull()?.let { friend ->
                val friendApplication = FriendApplication(friend.id, friend.firstName, friend.lastName, request.message)
                listApplication.add(friendApplication)
            }
        }
        listApplication
    }

    fun addFriendRequest(friendRequestDto: FriendRequestDto) = Either.unsafeCatch {
        friendRepository.getFriendRequestFromMe(friendRequestDto.fromUserId).let { myRequests ->
            if (!myRequests.asSequence().any { request -> request.userId == friendRequestDto.userId }) {
                friendRepository.saveRequest(friendRequestDto)
            }
        }
    }

    fun deleteFriendRequest(userId: Long, fromUserId: Long) = Either.unsafeCatch {
        friendRepository.deleteFriendRequest(userId, fromUserId)
    }

}