package com.example.blog

import org.springframework.stereotype.Service
import java.util.*


@Service
class Userservice(private val Repo: Repo){

    fun findUsers(): List<User>? = Repo.UserRead("all", "all")

    fun findUserByName(name: String): List<User>? = Repo.UserRead("Name", name)

    fun findUserByID(id: String): List<User>? = Repo.UserRead("ID", id)

    fun createUser(user: User): User{
        val id = UUID.randomUUID().toString()
        Repo.Update("users", id, user.name)
        return user.copy(id = id)
    }
}