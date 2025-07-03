package com.example.blog


import org.springframework.stereotype.Service

@Service
class Yoservice(private val Repo: YoRepo) {

    fun sendyos(senderID: String, receiverID: String): Yo {
        Repo.YoUpdate(senderID, receiverID)

        return Yo(senderID = senderID, receiverID = receiverID)
    }

    fun recyos(users: List<User>?): List<Yo>? {
        val receiverID: String? = users?.elementAt(0)?.id
        val yos = Repo.YoRead(receiverID)
        return yos
    }
}