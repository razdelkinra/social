package com.otus.social.controller

import arrow.core.Either
import com.otus.social.dto.FriendRequest
import com.otus.social.dto.UserDto
import com.otus.social.model.BadRequest
import com.otus.social.model.Failure
import com.otus.social.service.UserService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping
@CrossOrigin(origins = ["*"])
class UserController(private val userService: UserService) {

    @ApiOperation(value = "Get user")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "User is found"),
                ApiResponse(code = 204, response = UserDto::class, message = "User not found"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data")
            ]
    )
    @GetMapping("/api/users")
    fun getUser(@ApiIgnore authentication: Authentication) = when (val result = userService.getUserByLogin(getLoginFromToken(authentication))) {
        is Either.Left -> when (result.a) {
            else -> ResponseEntity.badRequest().body(result.a.message)
        }
        is Either.Right -> if (result.b == null) ResponseEntity.noContent().build() else ResponseEntity.ok(result.b!!)
    }

    private fun getLoginFromToken(authentication: Authentication) =
            (authentication.principal as User).username

    @ApiOperation(value = "New user registration")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "User is saved"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data")
            ]
    )
    @PostMapping("/registration")
    fun saveUser(@RequestBody user: UserDto) = when (val result = userService.addUser(user)) {
        is Either.Left -> when (result.a) {
            else -> ResponseEntity.badRequest().body(result.a.message)
        }
        is Either.Right -> ResponseEntity.ok(result.b)
    }

    @ApiOperation(value = "Update user")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "User is saved"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data"),
                ApiResponse(code = 500, response = Failure::class, message = "Error occurred while user saving")
            ]
    )
    @PutMapping("/api/users")
    fun updateUser(@ApiIgnore authentication: Authentication, @RequestBody user: UserDto) =
            when (val result = userService.updateUser(getLoginFromToken(authentication), user)) {
                is Either.Left -> when (result.a) {
                    else -> ResponseEntity.badRequest().body(result.a.message)
                }
                is Either.Right -> ResponseEntity.ok(result.b)
            }

    @ApiOperation(value = "Add friends")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "Friend was added"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data"),
                ApiResponse(code = 500, response = Failure::class, message = "Error occurred while user saving")
            ]
    )
    @PostMapping("/api/users/friends")
    fun addFriend(@RequestBody friendRequest: FriendRequest) =
            when (val result = userService.addFriend(friendRequest.userId, friendRequest.friendId)) {
                is Either.Left -> when (result.a) {
                    else -> ResponseEntity.badRequest().body(result.a.message)
                }
                is Either.Right -> ResponseEntity.ok(result.b)
            }

    @ApiOperation(value = "Get friends")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "Friends where found"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data"),
                ApiResponse(code = 500, response = Failure::class, message = "Error occurred while user saving")
            ]
    )
    @GetMapping("/api/users/friends")
    fun getFriends(id: Long) = when (val result = userService.getFriends(id)) {
        is Either.Left -> when (result.a) {
            else -> ResponseEntity.badRequest().body(result.a.message)
        }
        is Either.Right -> ResponseEntity.ok(result.b)
    }
}
