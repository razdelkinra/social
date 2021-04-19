package com.otus.social.utils

import arrow.core.Either
import arrow.core.left
import arrow.core.nonFatalOrThrow
import arrow.core.right
import com.otus.social.SocialApplication
import com.otus.social.model.SocialUserDetails
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

private val logger = LoggerFactory.getLogger(SocialApplication::class.java)

fun <A> Either.Companion.unsafeCatch(f: () -> A): Either<Throwable, A> =
        try {
            f().right()
        } catch (t: Throwable) {
            t.nonFatalOrThrow().left()
        }

fun Authentication.getId() = (this.principal as SocialUserDetails).id

fun Authentication.getLogin() = (this.principal as User).username

fun handleResponse(f: () -> Either<Throwable, *>) : ResponseEntity<*> =
    when (val result = f()) {
        is Either.Left -> when (result.a) {
            else -> {
                logger.error("Error handle response with message $result.a.message")
                ResponseEntity.badRequest().body(result.a.message)
            }
        }
        is Either.Right -> {
            if (result.b == null) ResponseEntity.noContent().build() else ResponseEntity.ok(result.b as Any)
        }
    }
