package com.otus.social.utils

import arrow.core.Either
import arrow.core.left
import arrow.core.nonFatalOrThrow
import arrow.core.right
import com.otus.social.model.SocialUserDetails
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User

fun <A> Either.Companion.unsafeCatch(f: () -> A): Either<Throwable, A> =
        try {
            f().right()
        } catch (t: Throwable) {
            t.nonFatalOrThrow().left()
        }

fun Authentication.getId() = (this.principal as SocialUserDetails).id

fun Authentication.getLogin() = (this.principal as User).username