package com.otus.social.entity

import com.otus.social.model.Gender
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "users")
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        val firstName: String,
        val lastName: String,
        @Column(name = "birthday")
        val birthDay: LocalDate? = null,
        val gender: String = Gender.UNKNOWN.name,
        val city: String? = "",
        val interest: String = "",
        @ManyToMany
        @JoinTable(name = "friend_request", joinColumns = [JoinColumn(name = "id")], inverseJoinColumns = [JoinColumn(name = "friend_id")])
        val friends: Set<User> = hashSetOf()
)