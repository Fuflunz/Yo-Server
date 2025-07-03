package com.example.blog

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Repository

@Repository
class YoRepo(private val db: JdbcTemplate){

    fun YoUpdate(valueOne: String, valueTwo: String) =
        db.update (
            "insert into yos Values ( ?, ? )",
            valueOne, valueTwo
        )

    fun YoRead(receiverID: String?): List<Yo>? = db.query("select * from yos where ReceiverID = ?", receiverID!!) { response, _ ->
        Yo(response.getString("SenderID"), response.getString("ReceiverID"))
    }
}