package com.josealonso.com.josealonso.repository

import com.josealonso.com.josealonso.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>