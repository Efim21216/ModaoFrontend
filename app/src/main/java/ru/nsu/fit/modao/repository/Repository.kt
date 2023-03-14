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
        return app.api.getUser(AUTH + app.accessToken, app.userId)
    }

    suspend fun createGroup(group: Group): Response<Long> {
        return app.api.createGroup(AUTH + app.accessToken, group)
    }

    suspend fun createUser(user: User): Response<Long> {
        return app.api.createUser(user)
    }

    suspend fun getGroupExpenses(id: Long): Response<Array<Expense>> {
        return app.api.getGroupExpenses(AUTH + app.accessToken, id)
    }

    suspend fun createExpense(expense: Expense): Response<Long> {
        return app.api.createExpense(AUTH + app.accessToken, expense)
    }

    suspend fun addUserToGroup(userOrgId: Long, groupId: Long, userId: Long): Response<Long> {
        return app.api.addUserToGroup(AUTH + app.accessToken, userOrgId, groupId, userId)
    }

    suspend fun confirmEvent(eventId: Long): Response<Unit> {
        return app.api.confirmEvent(AUTH + app.accessToken, eventId)
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

    suspend fun getEventInfo(eventId: Long): Response<Expense> {
        return app.api.getEventInfo(AUTH + app.accessToken, eventId)
    }
    suspend fun getGroupUnconfirmedExpenses(groupId: Long): Response<Array<Expense>> {
        return app.api.getGroupUnconfirmedExpenses(AUTH + app.accessToken, groupId)
    }
}