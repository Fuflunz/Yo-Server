package com.example.blog

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(
    classes = [BlogApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppIntegrationTest {


    @Autowired
    lateinit var restTemplate: TestRestTemplate

    private val userRepomockk = mockk<UserRepo>()
    private val yoRepomockk = mockk<YoRepo>()

    @Test
    fun ShouldLeadToNotFound() {
        val result = restTemplate.getForEntity("/yos/Unsinn", List::class.java)
        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun ShouldCreateYo(){
        val senderID = "3.14"
        val receiverID = "2.71"

        every { userRepomockk.ExistsUser("ID", senderID) } returns true
        every { userRepomockk.ExistsUser("ID", receiverID) } returns true
        every { yoRepomockk.YoUpdate(senderID, receiverID) } returns 0

        val result = restTemplate.postForEntity("/sendyo", Yo(senderID, receiverID), Yo::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.CREATED, result.statusCode)
        assertEquals("meep", result.getBody()?.senderID)
    }
}
