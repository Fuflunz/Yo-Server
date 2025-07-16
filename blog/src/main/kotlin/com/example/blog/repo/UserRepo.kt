package com.example.blog

import com.example.blog.model.UserNoPw
import org.mindrot.jbcrypt.BCrypt
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Repository


@Repository
class UserRepo(private val db: JdbcTemplate){

    fun UserRead(character: String, characterValue: String): List<UserNoPw> {
        if (character == "all") {
            return db.query("select * from userswithpassword") { response, _ ->
                UserNoPw(response.getString("Name"), response.getString("ID"))
            }
        }
        else {
            return db.query("select * from userswithpassword where $character = ?", characterValue) { response, _ ->
                UserNoPw(response.getString("Name"), response.getString("ID"))
            }
        }
    }

    fun PasswordReader(ID: String): String {
        return db.query("select * from userswithpassword where ID = ?", ID) { response, _ ->
            response.getString("password")
        }.first()
    }

    fun ExistsUser(character: String, characterValue: String) = UserRead(character, characterValue).isNotEmpty()

    fun UserUpdate(name: String, id: String, password: String): Int {

        val hashedPassword = hashPassword(password)

        db.update(
            "insert into userswithpassword Values ( ?, ?, ? )",
            id, name, hashedPassword
        )

        return 0
    }

    fun hashPassword(password: String): String{
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun validatePassword(password: String, hashedPassword: String): Boolean{
        return BCrypt.checkpw(password, hashedPassword)
    }
    }