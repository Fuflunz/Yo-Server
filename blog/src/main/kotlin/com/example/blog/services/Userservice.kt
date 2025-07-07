package com.example.blog

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class Userservice(private val Repo: UserRepo){

    fun findUsers(): List<User>? = Repo.UserRead("all", "all")

    fun findUserByName(name: String): List<User>? {
        val user=Repo.UserRead("Name", name)
        check(user?.isNotEmpty() == true)
        return user
    }

    fun findUserByID(id: String): List<User>?{
        val user = Repo.UserRead("ID", id)
        check(user?.isNotEmpty() == true)
        return user
    }

    fun createUser(user: User): User {
        val id = UUID.randomUUID().toString()
        val idcheck = Repo.UserRead("ID", id)
        check(idcheck?.isEmpty() == true)
        Repo.UserUpdate(user.name, id)
        return user.copy(id = id)
    }
}