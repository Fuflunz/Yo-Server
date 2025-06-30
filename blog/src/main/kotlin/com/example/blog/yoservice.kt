package com.example.blog

import org.springframework.stereotype.Service
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import java.util.*
import kotlin.math.sin
import kotlin.random.Random

@Service
class yoservice(private val db: JdbcTemplate) {

    fun sendyos(users: List<User?>): yo {
        val IDsend = users.elementAt(0)?.ID
        val IDrec = users.elementAt(1)?.ID
        db.update(
            "insert into yos Values ( ?, ? )",
            IDsend, IDrec
        )

        return yo(SenderID = "IDsend", ReceiverID = "IDrec")

    }

    fun recyos(user: User?): List<yo>? {
        val ReceiverID: String? = user?.ID
        val yoList: List<yo>? = db.query("select * from yos where ReceiverID = ?", ReceiverID!!) { response, _ ->
            yo(response.getString("SenderID"), response.getString("ReceiverID"))
        }

        return yoList
    }


}