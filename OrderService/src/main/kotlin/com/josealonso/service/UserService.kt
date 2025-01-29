package com.josealonso.service

import com.josealonso.entity.UserDTO
import com.josealonso.exceptions.UserNotFoundException
import com.josealonso.extensions.copy
import com.josealonso.extensions.fromDTO
import com.josealonso.extensions.toDTO
import com.josealonso.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(user: UserDTO): UserDTO {
        val newUser = user.fromDTO()
        val savedUser = userRepository.save(newUser)
        return savedUser.toDTO()
    }

    fun getUserById(userId: Long): UserDTO {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException(userId)
        return user.toDTO()
    }

    fun getAllUsers(): List<UserDTO> {
        return userRepository.findAll().map { it.toDTO() }
    }

    fun deleteUserById(userId: Long) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId)
        }
        else {
            throw UserNotFoundException(userId)
        }
    }

    fun updateUser(userId: Long, user: UserDTO): UserDTO {
        val existingUser = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException(userId)
        val updatedUser = existingUser.copy()
        return userRepository.save(updatedUser).toDTO()
    }

}
