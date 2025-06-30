package com.example.blog

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import java.net.URI

@RestController
@RequestMapping("/")

class Usercontroller(private val yoservice: yoservice, private val userservice: Userservice){
    @GetMapping
    fun listUsers() = userservice.findUsers()

    @GetMapping("/User/{Name}")
    fun UserbyName(@PathVariable Name: String): ResponseEntity<List<User>> =
        userservice.findUserbyName(Name).toUsersEntity()

    @GetMapping("/User/ID/{ID}")
    fun getUserbyID(@PathVariable ID: String): ResponseEntity<User> =
        userservice.findUserbyID(ID).toResponseEntity()

    private fun User?.toResponseEntity(): ResponseEntity<User> =
        // If the message is null (not found), set response code to 404
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    private fun List<User>?.toUsersEntity(): ResponseEntity<List<User>> =
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @GetMapping("/yos/{ID}")
    fun getyos( @PathVariable ID: String): ResponseEntity<List<yo>> =
        yoservice.recyos(userservice.findUserbyID(ID)).toYoEntity()

    private fun List<yo>?.toYoEntity(): ResponseEntity<List<yo>> =
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping("/createUser")
    fun create(@RequestBody user: User): ResponseEntity<User>{
        val savedUser = userservice.createUser(user)
        return ResponseEntity.created(URI("/${savedUser.ID}")).body(savedUser)
    }

    @PostMapping("/sendyo")
    fun send(@RequestBody Yo: yo): ResponseEntity<yo>{
        val User1 = userservice.findUserbyID(Yo.SenderID)
        val User2 = userservice.findUserbyID(Yo.ReceiverID)
        val talkers: List<User?> = listOf(User1, User2)
        val savedyo = yoservice.sendyos(talkers)

        return ResponseEntity.created(URI("/${savedyo.ReceiverID}")).body(savedyo)
        }
    }

