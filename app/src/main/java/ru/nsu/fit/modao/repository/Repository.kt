package ru.nsu.fit.modao.repository

import retrofit2.Response
import ru.nsu.fit.modao.models.Group
import ru.nsu.fit.modao.models.User
import ru.nsu.fit.modao.utils.App

class Repository(private val app: App) {
    suspend fun login(user: User): Response<Long> {
        return app.api.login(user)
    }
    suspend fun getUser(id: Long): Response<User>{
        return app.api.getUser(id)
    }
    suspend fun createGroup(id: Long, group: Group): Response<Long>{
        return app.api.createGroup(id, group)
    }
    suspend fun createUser(user: User): Response<Long>{
        return app.api.createUser(user)
    }
}