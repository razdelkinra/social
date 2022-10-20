package com.otus.social.controller

import com.otus.social.dto.request.FriendApproveDto
import com.otus.social.dto.request.FriendRequestDto
import com.otus.social.dto.request.UserDto
import com.otus.social.model.BadRequest
import com.otus.social.model.Failure
import com.otus.social.service.FriendRequestService
import com.otus.social.service.FriendService
import com.otus.social.utils.getId
import com.otus.social.utils.saveResponse
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping
@CrossOrigin(origins = ["*"])
class FriendController(
        private val friendService: FriendService,
        private val friendRequestService: FriendRequestService
) {

    @ApiOperation(value = "Get friend list")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "Friends where found"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data"),
                ApiResponse(code = 500, response = Failure::class, message = "Error occurred while user saving")
            ]
    )
    @GetMapping("/api/friends")
    fun getFriends(@ApiIgnore authentication: Authentication) =
            saveResponse { friendService.getFriends(authentication.getId()) }


    @ApiOperation(value = "Add friend")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "Friends where found"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data"),
                ApiResponse(code = 500, response = Failure::class, message = "Error occurred while user saving")
            ]
    )
    @PostMapping("/api/friends")
    fun addFriend(@ApiIgnore authentication: Authentication, @RequestBody friendDto: FriendApproveDto) =
            saveResponse { friendService.addFriend(authentication.getId(), friendDto.friendId) }

    @ApiOperation(value = "Get friend request list")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "Friends where found"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data"),
                ApiResponse(code = 500, response = Failure::class, message = "Error occurred while user saving")
            ]
    )
    @GetMapping("/api/friends/requests")
    fun getFriendRequests(@ApiIgnore authentication: Authentication) =
            saveResponse { friendRequestService.getFriendRequest(authentication.getId()) }

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
            saveResponse { friendRequestService.addFriendRequest(friendRequestDto.userId, friendRequestDto.fromUserId) }

}