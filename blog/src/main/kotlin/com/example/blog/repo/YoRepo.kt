package com.example.blog

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Repository

@Repository
class YoRepo(private val db: JdbcTemplate){

    fun YoUpdate(id1: String, id2: String) =
        db.update (
            "insert into yos Values ( ?, ? )",
            id1, id2
        )

    fun YoRead(receiverID: String?): List<Yo>? = db.query("select * from yos where ReceiverID = ?", receiverID!!) { response, _ ->
        Yo(response.getString("SenderID"), response.getString("ReceiverID"))
    }
}