package ru.nsu.fit.modao.repository

import retrofit2.Response
import ru.nsu.fit.modao.models.*
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.utils.Constants

class MainRepository (private val app: App) {
    suspend fun login(user: User): Response<Authorization> {
        return app.api.login(user)
    }
    suspend fun createUser(user: User): Response<Long> {
        return app.api.createUser(user)
    }

    suspend fun getUser(): Response<User> {
        return app.api.getUser(Constants.AUTH + app.accessToken)
    }

    suspend fun createGroup(group: Group): Response<Long> {
        return app.api.createGroup(Constants.AUTH + app.accessToken, group)
    }


    suspend fun getGroupExpenses(id: Long, mode: Int, type: Int): Response<Array<Expense>> {
        return app.api.getGroupExpenses(Constants.AUTH + app.accessToken, id, mode, type)
    }

    suspend fun createExpense(expense: Expense): Response<Long> {
        return app.api.createExpense(Constants.AUTH + app.accessToken, expense)
    }

    suspend fun addUserToGroup(groupId: Long, userId: Long): Response<Unit> {
        return app.api.addUserToGroup(Constants.AUTH + app.accessToken, groupId, userId)
    }

    suspend fun confirmEvent(groupId: Long, eventId: Long): Response<Unit> {
        return app.api.confirmEvent(Constants.AUTH + app.accessToken, groupId, eventId)
    }

    suspend fun getUsersInGroup(groupId: Long): Response<Array<User>> {
        return app.api.getUsersInGroup(Constants.AUTH + app.accessToken, groupId)
    }

    suspend fun getUserDebtInGroup(userId: Long, groupId: Long): Response<Array<UserDebt>> {
        return app.api.getUserDebtInGroup(Constants.AUTH + app.accessToken, userId, groupId)
    }

    suspend fun getListOrganizers(groupId: Long): Response<Array<User>> {
        return app.api.getListOrganizers(Constants.AUTH + app.accessToken, groupId)
    }

    suspend fun notConfirmEvent(eventId: Long): Response<Unit> {
        return app.api.notConfirmEvent(Constants.AUTH + app.accessToken, eventId)
    }

    suspend fun getEventInfo(eventId: Long, groupId: Long): Response<Expense> {
        return app.api.getEventInfo(Constants.AUTH + app.accessToken, eventId, groupId)
    }
    suspend fun getGroupUnconfirmedExpenses(groupId: Long): Response<Array<Expense>> {
        return app.api.getGroupUnconfirmedExpenses(Constants.AUTH + app.accessToken, groupId)
    }
    suspend fun getUserGroups(): Response<Array<Group>>{
        return app.api.getUserGroups(Constants.AUTH + app.accessToken)
    }
    suspend fun getGroupInfo(groupId: Long): Response<Group>{
        return app.api.getGroupInfo(Constants.AUTH + app.accessToken ,groupId)
    }
    suspend fun addFriend(userUuid: String): Response<Unit> {
        return app.api.addFriend(Constants.AUTH + app.accessToken, userUuid)
    }
    suspend fun getInvitationsFriend(): Response<Array<Notification>> {
        return app.api.getInvitationsFriend(Constants.AUTH + app.accessToken)
    }
    suspend fun getInvitationsGroup(): Response<Array<Notification>> {
        return app.api.getInvitationsGroup(Constants.AUTH + app.accessToken)
    }
    suspend fun acceptInvitationFriend(invitationId: Long):Response<Unit> {
        return app.api.acceptInvitationFriend(Constants.AUTH + app.accessToken, invitationId)
    }
    suspend fun denyInvitationFriend(invitationId: Long):Response<Unit> {
        return app.api.denyInvitationFriend(Constants.AUTH + app.accessToken, invitationId)
    }
    suspend fun acceptInvitationGroup(invitationId: Long):Response<Unit> {
        return app.api.acceptInvitationGroup(Constants.AUTH + app.accessToken, invitationId)
    }
    suspend fun denyInvitationGroup(invitationId: Long):Response<Unit> {
        return app.api.denyInvitationGroup(Constants.AUTH + app.accessToken, invitationId)
    }
    suspend fun getListFriends(): Response<Array<User>> {
        return app.api.getListFriends(Constants.AUTH + app.accessToken)
    }
    suspend fun addToGroup(groupUUID: String): Response<Unit> {
        return app.api.addToGroup(Constants.AUTH + app.accessToken, groupUUID)
    }
}