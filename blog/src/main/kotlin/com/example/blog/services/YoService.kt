package com.example.blog

import com.example.blog.model.UserNoPw
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
@Service
class YoService(private val yoRepo: YoRepo, private val userRepo: UserRepo) {

    private val logger = KotlinLogging.logger {}

    fun sendyos(senderID: String, receiverID: String, password: String): Yo {
        check(userRepo.ExistsUser("ID", senderID))
        check(userRepo.ExistsUser("ID", receiverID))
        logger.info { "Users do exist, checking password" }
        check(userRepo.validatePassword(password, userRepo.PasswordReader(senderID)))
        logger.info { "password verified" }
        yoRepo.YoUpdate(senderID, receiverID)
        return Yo(senderID = senderID, receiverID = receiverID)
    }

    fun recyos(user: UserNoPw, password: String): List<Yo>{
        check(userRepo.validatePassword(password, userRepo.PasswordReader(user.id)))
        return yoRepo.YoRead(user.id)
    }
}