package com.josealonso.controller

import com.josealonso.com.josealonso.controller.UserController
import com.josealonso.com.josealonso.entity.UserDTO
import com.josealonso.com.josealonso.service.UserService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class UserControllerTest {

    private lateinit var userController: UserController
    private lateinit var userService: UserService

    private val userDTOExample1 = UserDTO(
        1L,
        "alonso",
        email = "jose.alonso@me.com",
        phoneNumber = "123456789",
        orders = mutableListOf()
    )

    private val userDTOExample2 = UserDTO(
        2L,
        "jose",
        email = "joseph.alar@me.com",
        phoneNumber = "123776789",
        orders = mutableListOf()
    )

    @BeforeEach
    fun setup() {
        userService = mockk()
        userController = UserController(userService)
    }

    @Test
    fun `createUser should save and return the created user`() {
        val user = userDTOExample1
        every { userService.createUser(user) } returns user

        val result = userController.createUser(user)

        assertEquals(user, result)
        verify(exactly = 1) { userService.createUser(user) }
    }

    @Test
    fun `getAllUsers should return a list of all users`() {
        val users = listOf(userDTOExample1, userDTOExample2)
        every { userService.getAllUsers() } returns users

        val result = userController.getAllUsers()

        assertEquals(users, result)
        verify(exactly = 1) { userService.getAllUsers() }
    }

    @Test
    fun `getUserById should return the user with the given id`() {
        val userId = 1L
        val user = userDTOExample1
        every { userService.getUserById(userId) } returns user

        val result = userController.getUserById(userId)

        assertEquals(user, result)
        verify(exactly = 1) { userService.getUserById(userId) }
    }

    @Test
    fun `updateUser should update and return the user`() {
        val user = userDTOExample1
        every { userService.updateUser(user) } returns user

        val result = userController.updateUser(user)

        assertEquals(user, result)
        verify(exactly = 1) { userService.updateUser(user) }
    }

    @Test
    fun `deleteUser should delete the user with the given id`() {
        val userId = 1L
        every { userService.deleteUserById(userId) } returns Unit

        userController.deleteUserById(userId)

        verify(exactly = 1) { userService.deleteUserById(userId) }
    }

}