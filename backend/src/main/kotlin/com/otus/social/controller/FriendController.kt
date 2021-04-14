package com.otus.social.controller

import arrow.core.Either
import com.otus.social.dto.FriendRequestDto
import com.otus.social.dto.UserDto
import com.otus.social.model.BadRequest
import com.otus.social.model.Failure
import com.otus.social.service.FriendService
import com.otus.social.utils.getId
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping
@CrossOrigin(origins = ["*"])
class FriendController(private val friendService: FriendService) {

    @ApiOperation(value = "Make friend request")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "Friend was added"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data"),
                ApiResponse(code = 500, response = Failure::class, message = "Error occurred while user saving")
            ]
    )
    @PostMapping("/api/friends/request")
    fun addFriendRequest(@RequestBody friendRequestDto: FriendRequestDto) =
            when (val result = friendService.addFriendRequest(friendRequestDto)) {
                is Either.Left -> when (result.a) {
                    else -> ResponseEntity.badRequest().body(result.a.message)
                }
                is Either.Right -> ResponseEntity.ok("ok")
            }

    @ApiOperation(value = "Get friend list")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "Friends where found"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data"),
                ApiResponse(code = 500, response = Failure::class, message = "Error occurred while user saving")
            ]
    )
    @GetMapping("/api/friends")
    fun getFriends(@ApiIgnore authentication: Authentication) = when (val result = friendService.getFriends(authentication.getId())) {
        is Either.Left -> when (result.a) {
            else -> ResponseEntity.badRequest().body(result.a.message)
        }
        is Either.Right -> ResponseEntity.ok(result.b)
    }


    @ApiOperation(value = "Add friend")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "Friends where found"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data"),
                ApiResponse(code = 500, response = Failure::class, message = "Error occurred while user saving")
            ]
    )
    @PostMapping("/api/friends")
    fun addFriend(@ApiIgnore authentication: Authentication, friendId: Long) = when (val result = friendService.addFriend(authentication.getId(), friendId)) {
        is Either.Left -> when (result.a) {
            else -> ResponseEntity.badRequest().body(result.a.message)
        }
        is Either.Right -> ResponseEntity.ok(result.b)
    }

    @ApiOperation(value = "Get friend request list")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "Friends where found"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data"),
                ApiResponse(code = 500, response = Failure::class, message = "Error occurred while user saving")
            ]
    )
    @GetMapping("/api/friends/requests")
    fun getFriendRequests(@ApiIgnore authentication: Authentication) = when (val result = friendService.getFriendRequest(authentication.getId())) {
        is Either.Left -> when (result.a) {
            else -> ResponseEntity.badRequest().body(result.a.message)
        }
        is Either.Right -> ResponseEntity.ok(result.b)
    }

}