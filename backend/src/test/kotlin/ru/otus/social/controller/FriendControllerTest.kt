package ru.otus.social.controller

import com.otus.social.SocialApplication
import com.otus.social.controller.FriendController
import com.otus.social.dto.CredentialDto
import com.otus.social.dto.request.FriendRequestDto
import com.otus.social.dto.request.UserDto
import com.otus.social.model.Gender
import com.otus.social.service.ClientService
import com.otus.social.service.FriendRequestService
import com.otus.social.service.FriendService
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
class FriendControllerTest {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var friendService: FriendService

    @Autowired
    lateinit var friendRequestService: FriendRequestService

    @Autowired
    lateinit var clientService: ClientService

    @Autowired
    lateinit var controller: FriendController

    @Test
    fun shouldAddFriend() {
        val (login, firstName, user) = getUser("John")
        val user2 = getUser2("Tom")
        val authentication = Mockito.mock(Authentication::class.java)
        val userDetails = User(login, "123456", true, true, true, true, AuthorityUtils.createAuthorityList("USER"))
        Mockito.`when`(authentication.principal).thenReturn(userDetails)

        val id1 = userService.saveUser(user)
        val id2 = userService.saveUser(user2.third)

        val friendRequest = FriendRequestDto(id1, id2, "Add me please", LocalDate.now())

        controller.addFriendRequest(friendRequest)

        val requests = friendRequestService.getFriendRequest(id1)
        requests[0]
        Assert.assertNotNull(requests)
        Assert.assertTrue(requests[0].id == id2)
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

