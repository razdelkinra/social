package com.otus.social.controller

import com.otus.social.dto.request.FilterUserDto
import com.otus.social.dto.request.UserDto
import com.otus.social.model.BadRequest
import com.otus.social.model.Failure
import com.otus.social.service.UserService
import com.otus.social.utils.getId
import com.otus.social.utils.getLogin
import com.otus.social.utils.handleResponse
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
class UserController(private val userService: UserService) {

    @ApiOperation(value = "Get user profile")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "User is found"),
                ApiResponse(code = 204, response = UserDto::class, message = "User not found"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data")
            ]
    )
    @GetMapping("/api/profile")
    fun getProfile(@ApiIgnore authentication: Authentication) =
            handleResponse { userService.getUserByLogin(authentication.getLogin()) }

    @ApiOperation(value = "Get users")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "User is found"),
                ApiResponse(code = 204, response = UserDto::class, message = "User not found"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data")
            ]
    )
    @GetMapping("/api/users")
    fun getUsers(@ApiIgnore authentication: Authentication) = handleResponse { userService.getUsers(authentication.getId()) }

    @ApiOperation(value = "New user registration")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "User is saved"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data")
            ]
    )
    @PostMapping("/registration")
    fun saveUser(@RequestBody user: UserDto) = handleResponse { userService.addUser(user) }

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
            handleResponse { userService.updateUser(authentication.getLogin(), user) }


    @GetMapping("/api/users/generate")
    fun generateUser(count: String) {
        userService.generateUser(count.toInt())
        ResponseEntity.ok()
    }

    @ApiOperation(value = "Get users by filter")
    @ApiResponses(
            value = [
                ApiResponse(code = 200, response = UserDto::class, message = "User is saved"),
                ApiResponse(code = 400, response = BadRequest::class, message = "Incorrect request data")
            ]
    )
    @PostMapping("/api/users")
    fun getUsers(@RequestBody filterUserDto: FilterUserDto) {
        userService.getUsers(filterUserDto)
    }
}
