package ru.otus.social.controller

import com.otus.social.SocialApplication
import com.otus.social.controller.TokenController
import com.otus.social.dto.CredentialDto
import com.otus.social.dto.LoginDto
import com.otus.social.dto.request.UserDto
import com.otus.social.model.Gender
import com.otus.social.service.ClientService
import com.otus.social.service.UserService
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest(classes = [SocialApplication::class])
@Transactional
@Rollback
class TokenControllerTest {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var clientService: ClientService

    @Test
    fun shouldGetToken() {
        val controller = TokenController(clientService)
        val (login, firstName, user) = getUser("John")
        userService.saveUser(user)
        var client = clientService.findByLogin(login).let {
            val dto = LoginDto("jdillinger", it!!.password)
            val token = controller.getToken(dto).body as String
            Assert.assertTrue(token.length > 30)
        }

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
}

