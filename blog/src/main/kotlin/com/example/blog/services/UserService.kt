package com.example.blog

import com.example.blog.model.UserNoPw
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(private val repo: UserRepo){

    private val logger = KotlinLogging.logger {}

    fun findUsers(): List<UserNoPw>{
        logger.info { "reading in users" }
        return repo.UserRead("all", "all")
    }

    fun findUserByName(name: String): List<UserNoPw> {
        check(repo.ExistsUser("Name", name))
        logger.info { "Found" }
        return repo.UserRead("Name", name)
    }

    fun findUserByID(id: String): UserNoPw{
        check(repo.ExistsUser("ID", id))
        logger.info { "Found" }
        return repo.UserRead("ID", id).first()
    }

    fun createUser(username: String, password: String, passwordverify: String): UserNoPw {
        check(password == passwordverify)
        val id = UUID.randomUUID().toString()
        check(repo.ExistsUser("ID", id) == false)
        logger.info { "ID is unique!" }
        repo.UserUpdate(username, id, password)
        return UserNoPw(id = id, name = username)
    }
}