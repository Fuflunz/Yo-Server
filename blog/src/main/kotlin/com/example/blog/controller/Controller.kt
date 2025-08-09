package com.example.blog

import com.example.blog.model.UserCreator
import com.example.blog.model.UserNoPw
import com.example.blog.model.YoCreator
import com.zaxxer.hikari.util.Credentials
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import java.net.URI
import io.github.oshai.kotlinlogging.KotlinLogging

@RestController
@RequestMapping("/")

class Controller(private val yoservice: YoService, private val userservice: UserService){

    private val logger = KotlinLogging.logger {}

    @GetMapping
    fun listUsers(): List<UserNoPw>{
        logger.info { "list all Users" }
        return userservice.findUsers()
    }

    @GetMapping("/User/{name}")
    fun UserbyName(@PathVariable name: String): ResponseEntity<List<UserNoPw>> {
        logger.info{ "Searching for User with Name = $name"}
        return runCatching {userservice.findUserByName(name).toUsersEntity()
        }.getOrElse {logger.info{"not Found"};ResponseEntity.notFound().build() }
    }

    @GetMapping("/User/ID/{id}")
    fun getUserbyID(@PathVariable id: String): ResponseEntity<UserNoPw> {
        logger.info { "searching for User By ID" }
        return runCatching { userservice.findUserByID(id).toUserEntity() }.getOrElse {logger.info { "not Found" }; ResponseEntity.notFound().build() }
    }

    @PostMapping("/getyos")
    fun getyos( @RequestBody credentials: User): ResponseEntity<List<Yo>> {
        val id = credentials.id
        val password = credentials.password
        logger.info { "getting those yos" }
        return runCatching { yoservice.recyos(userservice.findUserByID(id), password).toYoEntity() }.getOrElse { ResponseEntity.notFound().build() }
    }

    @PostMapping("/createUser")
    fun create(@RequestBody usercreate: UserCreator): ResponseEntity<String> {
        logger.info{"creating an new User"}
        return runCatching {
            val savedUser = userservice.createUser(usercreate.username, usercreate.password, usercreate.passwordverify)
            ResponseEntity.created(URI("/${savedUser.id}")).body("user saved. name = ${savedUser.name} and id = ${savedUser.id}")
        }.getOrElse {
           logger.info { "passwordverification failed or password is invalid" }; ResponseEntity.created(URI("/noUser")).body("generated UUID doubled. YOU ARE LUCKY!!! Try again tho")
        }
    }

    @PostMapping("/sendyo")
    fun send(@RequestBody yo: YoCreator): ResponseEntity<Yo>{
        logger.info { "a Yo is getting send" }
        return runCatching {
            val savedYo = yoservice.sendyos(yo.senderID, yo.receiverID, yo.senderpassword)
            ResponseEntity.created(URI("/${savedYo.receiverID}")).body(savedYo)
        }.getOrElse {
            logger.info{"participants are not existent or wrong password"};ResponseEntity.notFound().build()
        }
    }

    private fun UserNoPw.toUserEntity(): ResponseEntity<UserNoPw> =
        this.let { ResponseEntity.ok(it) }

    private fun List<UserNoPw>?.toUsersEntity(): ResponseEntity<List<UserNoPw>> =
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    private fun List<Yo>?.toYoEntity(): ResponseEntity<List<Yo>> =
        this?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }

