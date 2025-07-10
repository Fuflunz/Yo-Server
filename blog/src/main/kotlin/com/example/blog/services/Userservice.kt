package com.example.blog

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class Userservice(private val Repo: UserRepo){

    fun findUsers(): List<User> = Repo.UserRead("all", "all")

    fun findUserByName(name: String): List<User> {
        check(Repo.ExistsUser("Name", name))
        return Repo.UserRead("Name", name)
    }

    fun findUserByID(id: String): User{
        check(Repo.ExistsUser("ID", id))
        return Repo.UserRead("ID", id).first()
    }

    fun createUser(username: String): User {
        val id = UUID.randomUUID().toString()
        check(Repo.ExistsUser("ID", id) == false)
        Repo.UserUpdate(username, id)
        return User(id = id, name = username)
    }
}