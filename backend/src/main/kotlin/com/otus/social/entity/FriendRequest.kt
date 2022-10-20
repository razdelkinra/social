package com.otus.social.entity

import javax.persistence.*

@Entity(name = "friend_request")
class FriendRequest(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(name = "user_id")
        val userId: Long,
        @Column(name = "friend_id")
        val friendId: Long
)