package com.example.blog

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import java.net.URI

@RestController
@RequestMapping("/")

class Usercontroller(private val yoservice: Yoservice, private val userservice: Userservice){
    @GetMapping
    fun listUsers() = userservice.findUsers()

    @GetMapping("/User/{name}")
    fun UserbyName(@PathVariable name: String): ResponseEntity<List<User>> =
        userservice.findUserByName(name).toUsersEntity()

    @GetMapping("/User/ID/{id}")
    fun getUserbyID(@PathVariable id: String): ResponseEntity<List<User>> =
        userservice.findUserByID(id).toUsersEntity()

    @GetMapping("/yos/{ID}")
    fun getyos( @PathVariable ID: String): ResponseEntity<List<Yo>> =
        yoservice.recyos(userservice.findUserByID(ID)).toYoEntity()

    @PostMapping("/createUser")
    fun create(@RequestBody user: User): ResponseEntity<User>{
        val savedUser = userservice.createUser(user)
        return ResponseEntity.created(URI("/${savedUser.id}")).body(savedUser)
    }

    @PostMapping("/sendyo")
    fun send(@RequestBody yo: Yo): ResponseEntity<Yo>{
        val savedyo = yoservice.sendyos(yo.senderID, yo.receiverID)
        return ResponseEntity.created(URI("/${savedyo.receiverID}")).body(savedyo)
        }


    private fun User?.toResponseEntity(): ResponseEntity<User> =
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    private fun List<User>?.toUsersEntity(): ResponseEntity<List<User>> =
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    private fun List<Yo>?.toYoEntity(): ResponseEntity<List<Yo>> =
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }

