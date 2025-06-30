package com.example.blog

import org.springframework.stereotype.Service
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import java.util.*
import kotlin.math.sin
import kotlin.random.Random


@Service
class Userservice(private val db: JdbcTemplate){

    fun findUsers(): List<User> = db.query("select * from users"){response, _ ->
        User( response.getString("Name"), response.getString("ID"))
    }

    fun findUserbyName(Name: String): List<User>? = db.query("select * from users where Name = ?", Name) { response, _ ->
        User(response.getString("Name"), response.getString("ID"))
    }


    fun findUserbyID(ID: String): User? = db.query("select * from users where ID = ?", ID) { response, _ ->
        User(response.getString("Name"), response.getString("ID"))
    }.singleOrNull()

    fun createUser(user: User): User{
        val ID = UUID.randomUUID().toString()
        db.update(
            "insert into users Values ( ?, ? )",
            ID, user.Name
        )
        return user.copy(ID = ID)
    }
}