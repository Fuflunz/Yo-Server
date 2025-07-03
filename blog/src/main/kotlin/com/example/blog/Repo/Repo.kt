package com.example.blog

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Repository

@Repository
class Repo(private val db: JdbcTemplate){

    fun Update(table: String, valueOne: String, valueTwo: String) =
        db.update (
            "insert into $table Values ( ?, ? )",
            valueOne, valueTwo
        )

    fun UserRead(character: String, characterValue: String): List<User>? {
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

    fun YoRead(ReceiverID: String?): List<Yo>? = db.query("select * from yos where ReceiverID = ?", ReceiverID!!) { response, _ ->
        Yo(response.getString("SenderID"), response.getString("ReceiverID"))
    }
}