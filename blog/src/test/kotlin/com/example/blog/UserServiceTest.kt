package com.example.blog

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class Userservicetest {

    private val userRepomockk = mockk<UserRepo>()
    private val userservice = Userservice(userRepomockk)


    @Test
    fun findUserByName(){
        //given
        val Name = "Ama"
        every { userRepomockk.UserRead("Name", Name) } returns listOf(User("Ama", "27545430-d738-4a49-9f1c-f17a42198d6e"))

        //when
        val actual = userservice.findUserByName(Name)
        val expected = listOf(User("Ama", "27545430-d738-4a49-9f1c-f17a42198d6e"))

        //then
        assertEquals(expected, actual)
        verify(exactly = 1){userRepomockk.UserRead("Name", Name)}

    }
}