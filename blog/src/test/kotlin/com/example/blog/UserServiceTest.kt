package com.example.blog

import com.example.blog.model.UserNoPw
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import java.util.UUID

class Userservicetest {

    private val userRepomockk = mockk<UserRepo>()
    private val userservice = UserService(userRepomockk)


    @Test
    fun findUserByName(){
        //given
        val Name = "Ama"
        val password = "123blub"
        every { userRepomockk.UserRead("Name", Name) } returns listOf(UserNoPw("Ama", "27545430-d738-4a49-9f1c-f17a42198d6e"))
        every { userRepomockk.ExistsUser("Name", Name)} returns true

        //when
        val actual = userservice.findUserByName(Name)
        val expected = listOf(UserNoPw("Ama", "27545430-d738-4a49-9f1c-f17a42198d6e"))

        //then
        assertEquals(expected, actual)
        verify(exactly = 1){userRepomockk.UserRead("Name", Name)}
    }

    @Test
    fun findUserByID(){
        val id = "27545430-d738-4a49-9f1c-f17a42198d6e"
        val password = "123blub"
        every{userRepomockk.UserRead("ID", id)} returns listOf(UserNoPw("Ama", id))
        every { userRepomockk.ExistsUser("ID", id)} returns true

        val actual = userservice.findUserByID(id)
        val expected = UserNoPw("Ama", id)

        assertEquals(expected, actual)
        verify(exactly = 1){ userRepomockk.UserRead("ID", id) }
    }

    @Test
    fun createUser(){
        val name = "Euler"
        val id = "350e8400-e29b-41d4-a716-446655440000"
        val password = "2.71"
        val uuidSource = UUID.fromString(id)
        mockkStatic(UUID::class)
        every { UUID.randomUUID() } returns uuidSource
        every{userRepomockk.ExistsUser("ID", id)} returns false
        every{userRepomockk.UserUpdate(name, id, password)} returns 0

        val actual = userservice.createUser(name, password, password)
        val expected = UserNoPw(name, id)

        assertEquals(expected, actual)
        verify(exactly = 1){userRepomockk.UserUpdate(name, id, password)}
    }
}