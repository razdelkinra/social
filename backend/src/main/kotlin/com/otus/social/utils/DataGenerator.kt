package com.otus.social.utils

import com.github.javafaker.Faker
import com.otus.social.dto.CredentialDto
import com.otus.social.dto.request.UserDto
import com.otus.social.model.Gender
import com.otus.social.repository.UserRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneId
import kotlin.random.Random

@Component
class DataGenerator(private val userRepository: UserRepository) {

    fun generateUser(count : Int) {
        val faker = Faker()
        val genders = arrayOf("MALE","FEMALE")
        for (i in 1..count) {
            UserDto(
                    firstName = faker.name().firstName(),
                    lastName = faker.name().lastName(),
                    birthDay = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    gender = Gender.valueOf(genders[Random.nextInt(0, 1)]),
                    city = faker.address().city(),
                    interests = faker.animal().name() + ", " + faker.buffy().celebrities(),
                    credential = CredentialDto(
                            login = faker.name().username(),
                            password = "123456"
                    )
            ).let {
                userRepository.saveUser(it)
            }
        }
    }
}