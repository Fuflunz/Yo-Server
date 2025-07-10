package com.example.blog

import org.springframework.stereotype.Service
@Service
class Yoservice(private val yoRepo: YoRepo, private val userRepo: UserRepo) {

    fun sendyos(senderID: String, receiverID: String): Yo {
        check(userRepo.ExistsUser("ID", senderID))
        check(userRepo.ExistsUser("ID", receiverID))
        yoRepo.YoUpdate(senderID, receiverID)
        return Yo(senderID = senderID, receiverID = receiverID)
    }

    fun recyos(user: User) = yoRepo.YoRead(user.id)
}