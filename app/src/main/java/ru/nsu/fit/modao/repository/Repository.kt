package ru.nsu.fit.modao.repository

import retrofit2.Response
import ru.nsu.fit.modao.models.*
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.utils.Constants.Companion.AUTH

class Repository(private val app: App) {
    suspend fun login(user: User): Response<Authorization> {
        return app.api.login(user)
    }

    suspend fun getUser(): Response<User> {
        return app.api.getUser(AUTH + app.accessToken)
    }

    suspend fun createGroup(group: Group): Response<Long> {
        return app.api.createGroup(AUTH + app.accessToken, group)
    }

    suspend fun createUser(user: User): Response<Long> {
        return app.api.createUser(user)
    }

    suspend fun getGroupExpenses(id: Long, mode: Int, type: Int): Response<Array<Expense>> {
        return app.api.getGroupExpenses(AUTH + app.accessToken, id, mode, type)
    }

    suspend fun createExpense(expense: Expense): Response<Long> {
        return app.api.createExpense(AUTH + app.accessToken, expense)
    }

    suspend fun addUserToGroup(groupId: Long, userId: Long): Response<Unit> {
        return app.api.addUserToGroup(AUTH + app.accessToken, groupId, userId)
    }

    suspend fun confirmEvent(groupId: Long, eventId: Long): Response<Unit> {
        return app.api.confirmEvent(AUTH + app.accessToken, groupId, eventId)
    }

    suspend fun getUsersInGroup(groupId: Long): Response<Array<User>> {
        return app.api.getUsersInGroup(AUTH + app.accessToken, groupId)
    }

    suspend fun getUserDebtInGroup(userId: Long, groupId: Long): Response<Array<UserDebt>> {
        return app.api.getUserDebtInGroup(AUTH + app.accessToken, userId, groupId)
    }

    suspend fun getListOrganizers(groupId: Long): Response<Array<User>> {
        return app.api.getListOrganizers(AUTH + app.accessToken, groupId)
    }

    suspend fun notConfirmEvent(eventId: Long): Response<Unit> {
        return app.api.notConfirmEvent(AUTH + app.accessToken, eventId)
    }

    suspend fun getEventInfo(eventId: Long, groupId: Long): Response<Expense> {
        return app.api.getEventInfo(AUTH + app.accessToken, eventId, groupId)
    }
    suspend fun getGroupUnconfirmedExpenses(groupId: Long): Response<Array<Expense>> {
        return app.api.getGroupUnconfirmedExpenses(AUTH + app.accessToken, groupId)
    }
    suspend fun getUserGroups(): Response<Array<Group>>{
        return app.api.getUserGroups(AUTH + app.accessToken)
    }
    suspend fun getGroupInfo(groupId: Long): Response<Group>{
        return app.api.getGroupInfo(AUTH + app.accessToken ,groupId)
    }
    suspend fun addFriend(userUuid: String): Response<Unit> {
        return app.api.addFriend(AUTH + app.accessToken, userUuid)
    }
    suspend fun getInvitationsFriend(): Response<Array<Notification>> {
        return app.api.getInvitationsFriend(AUTH + app.accessToken)
    }
    suspend fun getInvitationsGroup(): Response<Array<Notification>> {
        return app.api.getInvitationsGroup(AUTH + app.accessToken)
    }
    suspend fun acceptInvitationFriend(invitationId: Long):Response<Unit> {
        return app.api.acceptInvitationFriend(AUTH + app.accessToken, invitationId)
    }
    suspend fun denyInvitationFriend(invitationId: Long):Response<Unit> {
        return app.api.denyInvitationFriend(AUTH + app.accessToken, invitationId)
    }
    suspend fun acceptInvitationGroup(invitationId: Long):Response<Unit> {
        return app.api.acceptInvitationGroup(AUTH + app.accessToken, invitationId)
    }
    suspend fun denyInvitationGroup(invitationId: Long):Response<Unit> {
        return app.api.denyInvitationGroup(AUTH + app.accessToken, invitationId)
    }
    suspend fun getListFriends(): Response<Array<User>> {
        return app.api.getListFriends(AUTH + app.accessToken)
    }
    suspend fun addToGroup(groupUUID: String): Response<Unit> {
        return app.api.addToGroup(AUTH + app.accessToken, groupUUID)
    }
}