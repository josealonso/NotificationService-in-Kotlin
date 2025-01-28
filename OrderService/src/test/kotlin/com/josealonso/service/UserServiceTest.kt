package com.josealonso.service

import com.josealonso.com.josealonso.entity.UserDTO
import com.josealonso.com.josealonso.extensions.fromDTO
import com.josealonso.com.josealonso.repository.UserRepository
import com.josealonso.com.josealonso.service.UserService
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.data.repository.findByIdOrNull
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserServiceTest {

    private lateinit var userService: UserService
    private lateinit var userRepository: UserRepository

    val userDTOExample1 = UserDTO(
        userId = 1L,
        name = "Jose Alonso",
        email = "jose@example.com",
        phoneNumber = "123456789",
        orders = mutableListOf()
    )

    val userDTOExample2 = UserDTO(
        userId = 2L,
        name = "Peter",
        email = "peter@example.com",
        phoneNumber = "2345678909",
        orders = mutableListOf()
    )

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        userService = UserService(userRepository)
    }

    @Test
    fun `createUser should save and return the created user`() {
        every { userRepository.save(any()) } returns userDTOExample1.fromDTO()

        val result = userService.createUser(userDTOExample1)

        assertNotNull(result)
        assertEquals(userDTOExample1, result)
        verify(exactly = 1) { userRepository.save(any()) }
    }

    @Test
    fun `getAllUsers should return a list of all users`() {
        val users = listOf(userDTOExample1, userDTOExample2)
        every { userRepository.findAll() } returns users.map { it.fromDTO() }

        val result = userService.getAllUsers()

        assertNotNull(result)
        assertEquals(users, result)
        verify(exactly = 1) { userRepository.findAll() }
    }

    @Test
    fun `getUserById should return the user with the given id`() {
        val userId = userDTOExample1.userId
        every { userRepository.findByIdOrNull(userId) } returns userDTOExample1.fromDTO()

        val result = userService.getUserById(userId)

        assertNotNull(result)
        assertEquals(userDTOExample1, result)
        verify(exactly = 1) { userRepository.findByIdOrNull(userId) }
    }

    @Test
    fun `updateUser should update and return the user`() {
        val userId = userDTOExample1.userId

        every { userRepository.findByIdOrNull(userId) } returns userDTOExample1.fromDTO()
        every { userRepository.save(any()) } returns userDTOExample1.fromDTO()

        val result = userService.updateUser(userId, userDTOExample1)

        assertNotNull(result)
        assertEquals(userDTOExample1, result)
        verify(exactly = 1) { userRepository.findByIdOrNull(userId) }
        verify(exactly = 1) { userRepository.save(any()) }
    }

    @Test
    fun `deleteUser should delete the user with the given id`() {
        val userId = userDTOExample1.userId
        every { userRepository.existsById(userId) } returns true
        every { userRepository.deleteById(userId) } just Runs

        userService.deleteUserById(userId)

        verify(exactly = 1) { userRepository.deleteById(userId) }
    }

}