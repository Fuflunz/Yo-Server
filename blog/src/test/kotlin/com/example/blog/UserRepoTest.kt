package com.example.blog

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query

class UserRepoTest{

    private val db = mockk<JdbcTemplate>()
    private val userrepo = UserRepo(db = db)

    @Test
    fun UserRead(){
        val Users = listOf(
            User("Feynman", "7,29*10^(-3)","7,29*10^(-3)"),
            User("Planck", "6,62*10^(-34)","6,62*10^(-34)"),
            User("Einstein", "1,9*10^(-26)","1,9*10^(-26)")
            )

        every { db.query("select * from users") { response, _ ->
            User(response.getString("Name"), response.getString("ID"), response.getString("password"))
        } } returns Users

        every { db.query("select * from users where ID = ?", Users.first().id) { response, _ ->
            User(response.getString("Name"), response.getString("ID"), response.getString("password"))
        } } returns listOf(Users.first())

        every { db.query("select * from users where Name = ?", Users.elementAt(1).name) { response, _ ->
            User(response.getString("Name"), response.getString("ID"), response.getString("password"))
        } } returns listOf(Users.elementAt(1))

        val actual1 = userrepo.UserRead("all", "all")
        val actual2 = userrepo.UserRead("ID", Users.first().id)
        val actual3 = userrepo.UserRead("Name", Users.elementAt(1).name)

        val expected1 = Users
        val expected2 = listOf(Users.elementAt(0))
        val expected3 = listOf(Users.elementAt(1))

        assertEquals(expected1, actual1)
        assertEquals(expected2, actual2)
        assertEquals(expected3, actual3)

        verify(exactly = 1){db.query("select * from users") { response, _ ->
            User(response.getString("Name"), response.getString("ID"), response.getString("password"))
        }}
        verify(exactly = 1){db.query("select * from users where ID = ?", Users.first().id) { response, _ ->
            User(response.getString("Name"), response.getString("ID"), response.getString("password"))
        }}
        verify(exactly = 1){db.query("select * from users where Name = ?", Users.elementAt(1).name) { response, _ ->
            User(response.getString("Name"), response.getString("ID"), response.getString("password"))
        }
        }}



    }
