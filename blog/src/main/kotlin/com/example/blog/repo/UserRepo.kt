package com.example.blog

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Repository

@Repository
class UserRepo(private val db: JdbcTemplate){

    fun UserRead(character: String, characterValue: String): List<User> {
        if (character == "all") {
            return db.query("select * from users") { response, _ ->
                User(response.getString("Name"), response.getString("ID"))
            }
        } else {
            return db.query("select * from users where $character = ?", characterValue) { response, _ ->
                User(response.getString("Name"), response.getString("ID"))
            }
        }
    }

    fun ExistsUser(character: String, characterValue: String) = UserRead(character, characterValue).isNotEmpty()

    fun UserUpdate(name: String, id: String) =
        db.update (
            "insert into users Values ( ?, ? )",
            id, name  //In SQL-Data id is first and name second
        )
}