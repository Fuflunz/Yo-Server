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

class Controller(private val yoservice: Yoservice, private val userservice: Userservice){
    @GetMapping
    fun listUsers() = userservice.findUsers()

    @GetMapping("/User/{name}")
    fun UserbyName(@PathVariable name: String): ResponseEntity<List<User>> {
        return runCatching { userservice.findUserByName(name).toUsersEntity() }.getOrElse { ResponseEntity.notFound().build() }
    }

    @GetMapping("/User/ID/{id}")
    fun getUserbyID(@PathVariable id: String): ResponseEntity<User> {
        return runCatching { userservice.findUserByID(id).toUserEntity() }.getOrElse { ResponseEntity.notFound().build() }
    }

    @GetMapping("/yos/{ID}")
    fun getyos( @PathVariable ID: String): ResponseEntity<List<Yo>> {
        return runCatching { yoservice.recyos(userservice.findUserByID(ID)).toYoEntity() }.getOrElse { ResponseEntity.notFound().build() }
    }

    @PostMapping("/createUser")
    fun create(@RequestBody username: String): ResponseEntity<String> {
        return runCatching {
            val savedUser = userservice.createUser(username)
            ResponseEntity.created(URI("/${savedUser.id}")).body("user saved. name = ${savedUser.name} and id = ${savedUser.id}")
        }.getOrElse {
            ResponseEntity.created(URI("/noUser")).body("generated UUID doubled. YOU ARE LUCKY!!! Try again tho")
        }
    }

    @PostMapping("/sendyo")
    fun send(@RequestBody yo: Yo): ResponseEntity<Yo>{
        return runCatching {
            val savedYo = yoservice.sendyos(yo.senderID, yo.receiverID)
            ResponseEntity.created(URI("/${savedYo.receiverID}")).body(savedYo)
        }.getOrElse {
            ResponseEntity.notFound().build()
        }
    }

    private fun User.toUserEntity(): ResponseEntity<User> =
        this.let { ResponseEntity.ok(it) }

    private fun List<User>?.toUsersEntity(): ResponseEntity<List<User>> =
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    private fun List<Yo>?.toYoEntity(): ResponseEntity<List<Yo>> =
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }

