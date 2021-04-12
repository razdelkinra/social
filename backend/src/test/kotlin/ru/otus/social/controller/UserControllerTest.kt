package ru.otus.social.controller

import arrow.core.right
import com.otus.social.SocialApplication
import com.otus.social.controller.UserController
import com.otus.social.dto.CredentialDto
import com.otus.social.dto.UserDto
import com.otus.social.model.Gender
import com.otus.social.model.SocialUserDetails
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
        val userFromController = controller.getProfile(authentication).right().orNull()?.body as UserDto
        Assert.assertTrue(userFromController.firstName == firstName)
    }

    @Test
    fun shouldGetUsers() {
        val controller = UserController(userService)
        val (login, firstName, user) = getUser("John")
        val (login2, firstName2, user2) = getUser("John2")
        val (login3, firstName3, user3) = getUser("John3")
        val authentication = Mockito.mock(Authentication::class.java)
        var id = userService.addUser(user).orNull()
        userService.addUser(user2)
        userService.addUser(user3)
        val userDetails = SocialUserDetails(login, "123456", true, true, true, true, AuthorityUtils.createAuthorityList("USER"))
        userDetails.id = id!!
        Mockito.`when`(authentication.principal).thenReturn(userDetails)
        val users = controller.getUsers(authentication).right().orNull()?.body as List<UserDto>
        Assert.assertTrue(users.size == 2)
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

    private fun getUser(userName: String): Triple<String, String, UserDto> {
        val login = userName
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

