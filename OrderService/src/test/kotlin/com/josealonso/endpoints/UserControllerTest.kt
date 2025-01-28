package com.josealonso.endpoints

import com.fasterxml.jackson.databind.ObjectMapper
import com.josealonso.com.josealonso.controller.UserController
import com.josealonso.com.josealonso.entity.UserDTO
import com.josealonso.com.josealonso.service.UserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
@ContextConfiguration(classes = [UserController::class])
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    // @MockBean   // deprecated since Spring Boot 3.4.0
    @MockitoBean
    private lateinit var userService: UserService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    val userDTOExample = UserDTO(
        userId = 1L, name = "John Doe",
        email = "john@example.com",
        phoneNumber = "1234567890",
        orders = mutableListOf()
    )

    val userDTOExample2 = UserDTO(
        userId = 2L, name = "Jane Doe",
        email = "jane@example.com",
        phoneNumber = "9876543210",
        orders = mutableListOf()
    )

    @Test
    fun `should create user`() {
        val userDTO = userDTOExample

        `when`(userService.createUser(userDTO)).thenReturn(userDTO)

        mockMvc.perform(post("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.userId").value(userDTO.userId))
            .andExpect(jsonPath("$.name").value(userDTO.name))
            .andExpect(jsonPath("$.email").value(userDTO.email))
            .andExpect(jsonPath("$.phoneNumber").value(userDTO.phoneNumber))
            .andExpect(jsonPath("$.orders").isEmpty)
    }

    @Test
    fun `should get user by id`() {
        val userId = 1L
        val userDTO = userDTOExample
        `when`(userService.getUserById(userId)).thenReturn(userDTO)

        mockMvc.perform(get("/api/v1/users/$userId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userId").value(userDTO.userId))
            .andExpect(jsonPath("$.name").value(userDTO.name))
            .andExpect(jsonPath("$.email").value(userDTO.email))
            .andExpect(jsonPath("$.phoneNumber").value(userDTO.phoneNumber))
            .andExpect(jsonPath("$.orders").isEmpty)
    }

    @Test
    fun `should get all users`() {
        val users = listOf(userDTOExample, userDTOExample2)

        `when`(userService.getAllUsers()).thenReturn(users)

        mockMvc.perform(get("/api/v1/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].userId").value(users[0].userId))
            .andExpect(jsonPath("$[0].name").value(users[0].name))
            .andExpect(jsonPath("$[0].email").value(users[0].email))
            .andExpect(jsonPath("$[0].phoneNumber").value(users[0].phoneNumber))
            .andExpect(jsonPath("$[0].orders").isEmpty)
            .andExpect(jsonPath("$[1].userId").value(users[1].userId))
            .andExpect(jsonPath("$[1].name").value(users[1].name))
            .andExpect(jsonPath("$[1].email").value(users[1].email))
            .andExpect(jsonPath("$[1].phoneNumber").value(users[1].phoneNumber))
            .andExpect(jsonPath("$[1].orders").isEmpty)
    }

    @Test
    fun `should delete user`() {
        val userId = 1L

        mockMvc.perform(delete("/api/v1/users/$userId"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `should update user`() {
        val userId = userDTOExample.userId
        val userDTO = userDTOExample
        `when`(userService.updateUser(userId, userDTO)).thenReturn(userDTO)

        mockMvc.perform(put("/api/v1/users/$userId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userId").value(userDTO.userId))
            .andExpect(jsonPath("$.name").value(userDTO.name))
            .andExpect(jsonPath("$.email").value(userDTO.email))
            .andExpect(jsonPath("$.phoneNumber").value(userDTO.phoneNumber))
            .andExpect(jsonPath("$.orders").isEmpty)
    }
}
