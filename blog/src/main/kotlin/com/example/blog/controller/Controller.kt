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
    fun UserbyName(@PathVariable name: String): ResponseEntity<List<User>>{
        try {
            return userservice.findUserByName(name).toUsersEntity()
        }catch (e: IllegalStateException){
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/User/ID/{id}")
    fun getUserbyID(@PathVariable id: String): ResponseEntity<List<User>> {
        try{
            return userservice.findUserByID(id).toUsersEntity()
        }catch (e: IllegalStateException){
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/yos/{ID}")
    fun getyos( @PathVariable ID: String): ResponseEntity<List<Yo>> =
        yoservice.recyos(userservice.findUserByID(ID)).toYoEntity()

    @PostMapping("/createUser")
    fun create(@RequestBody user: User): ResponseEntity<String> {
        try {
            val savedUser = userservice.createUser(user)
            return ResponseEntity.created(URI("/${savedUser.id}")).body("user saved. name = ${savedUser.name} and id = ${savedUser.id}")
        }catch (e: IllegalStateException){
            return ResponseEntity.created(URI("/noUser")).body("id is taken")
        }
    }

    @PostMapping("/sendyo")
    fun send(@RequestBody yo: Yo): ResponseEntity<Yo>{
        try {
            val savedYo = yoservice.sendyos(yo.senderID, yo.receiverID)
            return ResponseEntity.created(URI("/${savedYo.receiverID}")).body(savedYo)
        }catch (e: IllegalStateException){
            return ResponseEntity.notFound().build()
        }
    }

    private fun List<User>?.toUsersEntity(): ResponseEntity<List<User>> =
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    private fun List<Yo>?.toYoEntity(): ResponseEntity<List<Yo>> =
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }

