package com.otus.social.utils

import com.github.javafaker.Faker
import com.otus.social.entity.Client
import com.otus.social.entity.User
import com.otus.social.model.Gender
import com.otus.social.repository.ClientRepository
import com.otus.social.repository.UserRepository
import org.springframework.stereotype.Component
import java.time.ZoneId
import kotlin.random.Random

@Component
class DataGenerator(
        private val userRepository: UserRepository,
        private var clientRepository: ClientRepository
) {

    fun generateUser(count: Int) {
        val faker = Faker()
        val genders = arrayOf("MALE", "FEMALE")
        for (i in 1..count) {
            User(
                    firstName = faker.name().firstName(),
                    lastName = faker.name().lastName(),
                    birthDay = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    gender = Gender.valueOf(genders[Random.nextInt(0, 1)]).name,
                    city = faker.address().city(),
                    interest = faker.animal().name() + ", " + faker.buffy().celebrities()
            ).let {
                val user = userRepository.save(it)
                clientRepository.save(Client(userId = user.id!!, login = faker.name().username(), password = "123456", token = ""))
            }
        }
    }
}