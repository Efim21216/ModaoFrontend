package ru.nsu.fit.modao.api

import retrofit2.Response
import retrofit2.http.*
import ru.nsu.fit.modao.models.*

interface ApiService {
    @POST("/api/auth/login")
    suspend fun login(@Body user: User): Response<Authorization>

    @POST("/api/auth/token")
    suspend fun getAccessToken(@Body refreshToken: Tokens): Response<Authorization>

    @POST("/api/auth/refresh")
    suspend fun getRefreshToken(@Body refreshToken: Tokens): Response<Authorization>

    @GET("/user/myInfo")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): Response<User>

    @GET("/user/exitUser")
    suspend fun exit(
        @Header("Authorization") token: String
    ): Response<Unit>

    @GET("/user/listGroups/{type}")
    suspend fun getUserGroups(
        @Header("Authorization") token: String,
        @Path("type") type: Int
    ): Response<Array<Group>>

    @GET("/group/delete/{groupId}")
    suspend fun deleteGroup(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Long
    ): Response<Unit>
    @GET("/group/archive/{groupId}")
    suspend fun archiveGroup(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Long
    ): Response<Unit>

    @POST("/group/create")
    suspend fun createGroup(
        @Header("Authorization") token: String,
        @Body group: Group
    ): Response<Long>

    @PUT("/group/addUserInGroup/{groupUUID}")
    suspend fun addToGroup(
        @Header("Authorization") token: String,
        @Path("groupUUID") groupUUID: String
    ): Response<Unit>

    @POST("/user/reg")
    suspend fun createUser(@Body user: User): Response<Long>

    @GET("/event/info/{groupId}/{eventId}")
    suspend fun getEventInfo(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: Long,
        @Path("groupId") groupId: Long
    ): Response<Expense>

    @PUT("/event/delete/{groupId}/{eventId}")
    suspend fun deleteEvent(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Long,
        @Path("eventId") eventId: Long,
        @Body name: String
    ): Response<Unit>

    @GET("/group/deleteUser/{groupId}/{userId}")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Long,
        @Path("userId") userId: Long
    ): Response<Unit>

    @GET("/group/archiveNo/{groupId}")
    suspend fun makeGroupActive(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Long
    ): Response<Unit>

    @GET("/event/listEventsConfirmed/{mode}/{groupId}/{type}/{minTime}/{maxTime}")
    suspend fun getGroupExpenses(
        @Header("Authorization") token: String,
        @Path("groupId") id: Long,
        @Path("mode") mode: Int,
        @Path("type") type: Int,
        @Path("minTime") minTime: Long,
        @Path("maxTime") maxTime: Long,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PagingExpenses>

    @GET("/event/listEventsUnconfirmed/{groupId}")
    suspend fun getGroupUnconfirmedExpenses(
        @Header("Authorization") token: String,
        @Path("groupId") id: Long
    ): Response<Array<Expense>>

    @POST("/event/create")
    suspend fun createExpense(
        @Header("Authorization") token: String,
        @Body expense: Expense
    ): Response<Long>

    @POST("/invitation/createInvitationInGroup/{groupId}/{userId}")
    suspend fun addUserToGroup(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Long,
        @Path("userId") userId: Long
    ): Response<Unit>

    @PUT("/event/confirmation/{groupId}/{eventId}")
    suspend fun confirmEvent(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Long,
        @Path("eventId") eventId: Long

    ): Response<Unit>

    @PUT("/event/unconfirmation/{groupId}/{eventId}")
    suspend fun notConfirmEvent(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Long,
        @Path("eventId") eventId: Long
    ): Response<Unit>

    @GET("/group/listUsers/{groupId}")
    suspend fun getUsersInGroup(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Long
    ): Response<Array<User>>

    @GET("/debt/{userId}/{groupId}")
    suspend fun getUserDebtInGroup(
        @Header("Authorization") token: String,
        @Path("userId") userId: Long,
        @Path("groupId") groupId: Long
    ): Response<Array<UserDebt>>

    @GET("/group/listOrganizers/{groupId}")
    suspend fun getListOrganizers(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Long
    ): Response<Array<User>>

    @GET("/group/info/{groupId}")
    suspend fun getGroupInfo(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: Long
    ): Response<Group>

    @GET("/invitation/getInvitationsFriend")
    suspend fun getInvitationsFriend(
        @Header("Authorization") token: String
    ): Response<Array<Notification>>

    @POST("/invitation/createInvitationFriend/{userUuid}")
    suspend fun addFriend(
        @Header("Authorization") token: String,
        @Path("userUuid") userUuid: String
    ): Response<Unit>

    @GET("/invitation/getInvitationsInGroup")
    suspend fun getInvitationsGroup(
        @Header("Authorization") token: String
    ): Response<Array<Notification>>

    @POST("/invitation/acceptInvitationFriend/{invitationId}")
    suspend fun acceptInvitationFriend(
        @Header("Authorization") token: String,
        @Path("invitationId") invitationId: Long
    ): Response<Unit>

    @POST("/invitation/declineInvitationFriend/{invitationId}")
    suspend fun denyInvitationFriend(
        @Header("Authorization") token: String,
        @Path("invitationId") invitationId: Long
    ): Response<Unit>

    @POST("/invitation/acceptInvitationInGroup/{invitationId}")
    suspend fun acceptInvitationGroup(
        @Header("Authorization") token: String,
        @Path("invitationId") invitationId: Long
    ): Response<Unit>

    @POST("/invitation/declineInvitationInGroup/{invitationId}")
    suspend fun denyInvitationGroup(
        @Header("Authorization") token: String,
        @Path("invitationId") invitationId: Long
    ): Response<Unit>

    @GET("/user/listFriends")
    suspend fun getListFriends(
        @Header("Authorization") token: String
    ): Response<Array<User>>
}