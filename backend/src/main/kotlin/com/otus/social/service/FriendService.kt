package com.otus.social.service

import arrow.core.Either
import com.otus.social.dto.FriendRequestDto
import com.otus.social.repository.FriendRepository
import com.otus.social.utils.unsafeCatch
import org.springframework.stereotype.Service

@Service
class FriendService(
        private val friendRepository: FriendRepository) {

    fun addFriend(userId: Long, friendId: Long) = Either.unsafeCatch { friendRepository.addFriend(userId, friendId) }

    fun getFriends(userId: Long) = Either.unsafeCatch { friendRepository.getFriend(userId) }

    fun getFriendRequest(userId: Long) = Either.unsafeCatch { friendRepository.getFriendRequest(userId) }

    fun addFriendRequest(friendRequestDto: FriendRequestDto) = Either.unsafeCatch { friendRepository.saveRequest(friendRequestDto) }

}