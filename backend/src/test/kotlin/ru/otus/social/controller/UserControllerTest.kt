package ru.otus.social.controller

import arrow.core.right
import arrow.core.valid
import com.otus.social.SocialApplication
import com.otus.social.controller.UserController
import com.otus.social.dto.CredentialDto
import com.otus.social.dto.FriendRequest
import com.otus.social.dto.UserDto
import com.otus.social.model.Gender
import com.otus.social.service.ClientService
import com.otus.social.service.UserService
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest(classes = [SocialApplication::class])
@Transactional
@Rollback
class UserControllerTest {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var clientService: ClientService

    @Test
    fun shouldSaveUser() {
        val controller = UserController(userService)
        val (login, firstName, user) = getUser("John")
        controller.saveUser(user)
        val userFromDb = userService.getUserByLogin(login).orNull()
        Assert.assertTrue(userFromDb?.firstName != null)
        Assert.assertTrue(userFromDb?.firstName == firstName)
    }

    @Test
    fun shouldGetUser() {
        val controller = UserController(userService)
        val (login, firstName, user) = getUser("John")
        val authentication = Mockito.mock(Authentication::class.java)
        val userDetails = User(login, "123456", true, true, true, true, AuthorityUtils.createAuthorityList("USER"))
        Mockito.`when`(authentication.principal).thenReturn(userDetails)
        userService.addUser(user)
        val userFromController = controller.getUser(authentication).right().orNull()?.body as UserDto
        Assert.assertTrue(userFromController.firstName == firstName)
    }

    @Test
    fun shouldUpdateUser() {
        val controller = UserController(userService)
        val (login, firstName, user) = getUser("John")
        val authentication = Mockito.mock(Authentication::class.java)
        val userDetails = User(login, "123456", true, true, true, true, AuthorityUtils.createAuthorityList("USER"))
        Mockito.`when`(authentication.principal).thenReturn(userDetails)
        userService.addUser(user)
        val userFromDb = userService.getUserByLogin(login).orNull()
        Assert.assertTrue(userFromDb?.firstName == firstName)
        val credential = CredentialDto(login = login, password = "123456")
        val newUser = UserDto(
                id = userFromDb?.id,
                firstName = "Tom",
                lastName = "Dillinger",
                birthDay = LocalDate.now().minusYears(99),
                gender = Gender.MALE,
                city = "Boston",
                interests = "Banks",
                credential = credential
        )
        controller.updateUser(authentication, newUser)
        val newUserFromDb = userService.getUserByLogin(login).orNull()
        Assert.assertTrue(newUserFromDb?.firstName == "Tom")
    }

    @Test
    fun shouldAddFriend() {
        val controller = UserController(userService)
        val (login, firstName, user) = getUser("John")
        val user2 = getUser2("Tom")
        val authentication = Mockito.mock(Authentication::class.java)
        val userDetails = User(login, "123456", true, true, true, true, AuthorityUtils.createAuthorityList("USER"))
        Mockito.`when`(authentication.principal).thenReturn(userDetails)
        val id1 = userService.addUser(user).orNull()!!
        val id2 = userService.addUser(user2.third).orNull()!!
        val friendRequest = FriendRequest(id1, id2)
        val isAdded = controller.addFriend(friendRequest).right().orNull()?.body as Boolean
        Assert.assertTrue(isAdded)
        val friends = userService.getFriends(id1).orNull()
        val userDto = friends!![0]
        Assert.assertTrue(userDto.firstName == "Tom")
        var friends1 = controller.getFriends(id1).right().orNull()!!.body!! as List<UserDto>
        Assert.assertTrue(friends1[0].firstName == "Tom")
    }

    private fun getUser(userName: String): Triple<String, String, UserDto> {
        val login = "jdillinger"
        val credential = CredentialDto(login = login, password = "123456")
        val user = UserDto(
                firstName = userName,
                lastName = "Dillinger",
                birthDay = LocalDate.now().minusYears(99),
                gender = Gender.MALE,
                city = "Boston",
                interests = "Banks",
                credential = credential
        )
        return Triple(login, userName, user)
    }

    private fun getUser2(userName: String): Triple<String, String, UserDto> {
        val login = "bobama"
        val credential = CredentialDto(login = login, password = "123456")
        val user = UserDto(
                firstName = userName,
                lastName = "Obama",
                birthDay = LocalDate.now().minusYears(99),
                gender = Gender.MALE,
                city = "Boston",
                interests = "Banks",
                credential = credential
        )
        return Triple(login, userName, user)
    }
}

