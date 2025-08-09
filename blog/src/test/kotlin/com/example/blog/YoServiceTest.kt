package com.example.blog

import com.example.blog.model.UserNoPw
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify


class YoServiceTest {
    private val userRepomockk = mockk<UserRepo>()
    private val yoRepomockk = mockk<YoRepo>()
    private val yoservice = YoService(yoRepomockk, userRepomockk)

    @Test
    fun sendyos() {
        val senderID = "3.14"
        val receiverID = "2.71"
        val password = "password"

        every { userRepomockk.ExistsUser("ID", senderID) } returns true
        every { userRepomockk.ExistsUser("ID", receiverID) } returns true
        every { userRepomockk.PasswordReader(senderID) } returns password
        every { userRepomockk.validatePassword(password, password) } returns true
        every { userRepomockk.UserRead("ID", senderID) } returns listOf(UserNoPw("pi", senderID))
        every { yoRepomockk.YoUpdate(senderID, receiverID) } returns 0

        val actual = yoservice.sendyos(senderID, receiverID, password)
        val expected = Yo(senderID = senderID, receiverID = receiverID)

        assertEquals(expected, actual)
        verify(exactly = 1) { yoRepomockk.YoUpdate(senderID, receiverID) }

    }
}