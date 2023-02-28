package ru.nsu.fit.modao.api

import retrofit2.Response
import retrofit2.http.*
import ru.nsu.fit.modao.models.Authorization
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.models.Group
import ru.nsu.fit.modao.models.User

interface ApiService {
    @POST("/api/auth/login")
    suspend fun login(@Body user: User): Response<Authorization>

    @GET("/user/info/{id}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<User>

    @POST("/group/create")
    suspend fun createGroup(
        @Header("Authorization") token: String,
        @Body group: Group
    ): Response<Long>

    @POST("/user/reg")
    suspend fun createUser(@Body user: User): Response<Long>

    @GET("/event/listEventsConfirmed/{groupId}")
    suspend fun getGroupExpenses(
        @Header("Authorization") token: String,
        @Path("groupId") id: Long
    ): Response<Array<Expense>>

    @POST("/event/create")
    suspend fun createExpense(
        @Header("Authorization") token: String,
        @Body expense: Expense
    ): Response<Long>

    @PUT("/group/addUserInGroup{userOrgId}/{groupId}/{userId}")
    suspend fun addUserToGroup(
        @Header("Authorization") token: String,
        @Path("userOrgId") userOrgId: Long, @Path("groupId") groupId: Long,
        @Path("userId") userId: Long
    ): Response<Long>

    @PUT("/event/confirmation/{eventId}")
    suspend fun confirmEvent(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: Long
    ): Response<Unit>

    @GET("/group/listUsers/{groupId}")
    suspend fun getUsersInGroup(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Long
    ): Response<Array<User>>
}