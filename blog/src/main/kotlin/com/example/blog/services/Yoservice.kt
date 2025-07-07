package com.example.blog

import org.springframework.stereotype.Service

@Service
class Yoservice(private val yoRepo: YoRepo, private val userRepo: UserRepo) {

    fun sendyos(senderID: String, receiverID: String): Yo {
        val user1 = userRepo.UserRead("ID", senderID)
        val user2 = userRepo.UserRead("ID", receiverID)
        check(user1?.isNotEmpty() == true)
        check(user2?.isNotEmpty() == true)
        yoRepo.YoUpdate(senderID, receiverID)
        return Yo(senderID = senderID, receiverID = receiverID)
    }

    fun recyos(users: List<User>?): List<Yo>? {
        val receiverID: String? = users?.elementAt(0)?.id
        val yos = yoRepo.YoRead(receiverID)
        return yos
    }
}