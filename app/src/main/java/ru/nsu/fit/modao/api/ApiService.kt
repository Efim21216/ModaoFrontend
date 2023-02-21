package ru.nsu.fit.modao.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.nsu.fit.modao.models.Group
import ru.nsu.fit.modao.models.User

interface ApiService {
    @POST("/user/in")
    suspend fun login(@Body user: User): Response<Long>
    @GET("/user/{id}")
    suspend fun getUser(@Path("id") id: Long): Response<User>
    @POST("/group/{id}")
    suspend fun createGroup(@Path("id") id: Long, @Body group: Group): Response<Long>
    @POST("/user")
    suspend fun createUser(@Body user: User): Response<Long>
}