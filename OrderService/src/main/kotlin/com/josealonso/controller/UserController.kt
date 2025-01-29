package com.josealonso.controller

import com.josealonso.entity.UserDTO
import com.josealonso.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody user: UserDTO): ResponseEntity<UserDTO> {
        val createdUser = userService.createUser(user)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDTO> {
        val user = userService.getUserById(id)
        return ResponseEntity(user, HttpStatus.OK)
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDTO>> {
        val allUsers = userService.getAllUsers()
        return ResponseEntity(allUsers, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id: Long): ResponseEntity<Unit> {
        userService.deleteUserById(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val updatedUser = userService.updateUser(id, userDTO)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }

}
